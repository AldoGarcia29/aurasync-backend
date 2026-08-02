package com.aurasync.aura.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aurasync.aura.dto.WatchLoginRequest;
import com.aurasync.aura.dto.WatchLoginResponse;
import com.aurasync.aura.entity.Dispositivo;
import com.aurasync.aura.entity.SesionDispositivo;
import com.aurasync.aura.repository.DispositivoRepository;
import com.aurasync.aura.repository.SesionDispositivoRepository;

@Service
public class WatchAuthService {

    private static final int MAX_INTENTOS = 5;
    private static final int BLOQUEO_MINUTOS = 5;
    private static final int SESION_MINUTOS = 30;

    private final DispositivoRepository
            dispositivoRepository;

    private final SesionDispositivoRepository
            sesionRepository;

    private final PasswordEncoder passwordEncoder;
    private final SecureRandom secureRandom;

    public WatchAuthService(
            DispositivoRepository dispositivoRepository,
            SesionDispositivoRepository sesionRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.dispositivoRepository =
                dispositivoRepository;

        this.sesionRepository =
                sesionRepository;

        this.passwordEncoder = passwordEncoder;
        this.secureRandom = new SecureRandom();
    }

    @Transactional
    public WatchLoginResponse iniciarSesion(
            WatchLoginRequest request
    ) {
        Dispositivo dispositivo =
                dispositivoRepository
                        .findById(
                                request.dispositivoId()
                        )
                        .orElseThrow(
                                () ->
                                        new IllegalArgumentException(
                                                "Dispositivo o PIN incorrecto"
                                        )
                        );

        if (!dispositivo.isPinHabilitado() ||
                dispositivo.getPinHash() == null) {
            throw new IllegalStateException(
                    "El dispositivo no tiene un PIN configurado"
            );
        }

        LocalDateTime ahoraLocal =
                LocalDateTime.now(ZoneOffset.UTC);

        if (dispositivo.getBloqueadoHasta() != null &&
                dispositivo
                        .getBloqueadoHasta()
                        .isAfter(ahoraLocal)) {
            throw new IllegalStateException(
                    "Dispositivo bloqueado temporalmente"
            );
        }

        if (dispositivo.getBloqueadoHasta() != null &&
                !dispositivo
                        .getBloqueadoHasta()
                        .isAfter(ahoraLocal)) {
            dispositivo.setBloqueadoHasta(null);
            dispositivo.setIntentosFallidos(0);
        }

        boolean pinCorrecto =
                passwordEncoder.matches(
                        request.pin(),
                        dispositivo.getPinHash()
                );

        if (!pinCorrecto) {
            registrarIntentoFallido(
                    dispositivo,
                    ahoraLocal
            );
        }

        dispositivo.setIntentosFallidos(0);
        dispositivo.setBloqueadoHasta(null);
        dispositivo.setUltimaAutenticacion(
                ahoraLocal
        );

        dispositivoRepository.save(dispositivo);

        OffsetDateTime ahora =
                OffsetDateTime.now(ZoneOffset.UTC);

        revocarSesionesAnteriores(
                dispositivo.getId(),
                ahora
        );

        String token = generarToken();
        String tokenHash = calcularSha256(token);

        SesionDispositivo sesion =
                new SesionDispositivo();

        sesion.setDispositivoId(
                dispositivo.getId()
        );

        sesion.setTokenHash(tokenHash);
        sesion.setFechaInicio(ahora);

        sesion.setFechaExpiracion(
                ahora.plusMinutes(SESION_MINUTOS)
        );

        sesion.setRevocada(false);
        sesion.setFechaCierre(null);

        sesionRepository.save(sesion);

        return new WatchLoginResponse(
                true,
                dispositivo.getId(),
                dispositivo.getUsuarioId(),
                dispositivo.getNombre(),
                token,
                sesion.getFechaExpiracion(),
                "Inicio de sesión correcto"
        );
    }

    private void registrarIntentoFallido(
            Dispositivo dispositivo,
            LocalDateTime ahora
    ) {
        int intentos =
                dispositivo.getIntentosFallidos() + 1;

        dispositivo.setIntentosFallidos(intentos);

        if (intentos >= MAX_INTENTOS) {
            dispositivo.setBloqueadoHasta(
                    ahora.plusMinutes(
                            BLOQUEO_MINUTOS
                    )
            );

            dispositivoRepository.save(dispositivo);

            throw new IllegalStateException(
                    "Demasiados intentos. "
                            + "El dispositivo fue bloqueado "
                            + "durante cinco minutos"
            );
        }

        dispositivoRepository.save(dispositivo);

        int restantes = MAX_INTENTOS - intentos;

        throw new IllegalArgumentException(
                "PIN incorrecto. Intentos restantes: "
                        + restantes
        );
    }

    private void revocarSesionesAnteriores(
            Long dispositivoId,
            OffsetDateTime ahora
    ) {
        List<SesionDispositivo> sesiones =
                sesionRepository
                        .findByDispositivoIdAndRevocadaFalse(
                                dispositivoId
                        );

        for (SesionDispositivo sesion : sesiones) {
            sesion.setRevocada(true);
            sesion.setFechaCierre(ahora);
        }

        if (!sesiones.isEmpty()) {
            sesionRepository.saveAll(sesiones);
        }
    }

    private String generarToken() {
        byte[] bytes = new byte[32];

        secureRandom.nextBytes(bytes);

        return Base64
                .getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }

    public String calcularSha256(String value) {
        try {
            MessageDigest digest =
                    MessageDigest.getInstance(
                            "SHA-256"
                    );

            byte[] resultado =
                    digest.digest(
                            value.getBytes(
                                    StandardCharsets.UTF_8
                            )
                    );

            return convertirHexadecimal(resultado);
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException(
                    "No fue posible proteger el token",
                    exception
            );
        }
    }

    private String convertirHexadecimal(
            byte[] bytes
    ) {
        StringBuilder resultado =
                new StringBuilder();

        for (byte value : bytes) {
            resultado.append(
                    String.format(
                            "%02x",
                            value
                    )
            );
        }

        return resultado.toString();
    }

    @Transactional
    public void cerrarSesion(String token) {
        String tokenHash =
                calcularSha256(token);

        SesionDispositivo sesion =
                sesionRepository
                        .findByTokenHashAndRevocadaFalse(
                                tokenHash
                        )
                        .orElseThrow(
                                () ->
                                        new IllegalArgumentException(
                                                "La sesión no es válida"
                                        )
                        );

        sesion.setRevocada(true);
        sesion.setFechaCierre(
                OffsetDateTime.now(
                        ZoneOffset.UTC
                )
        );

        sesionRepository.save(sesion);
    }


    @Transactional
public SesionDispositivo validarSesion(
        String token
) {
    if (token == null || token.isBlank()) {
        throw new IllegalArgumentException(
                "La sesión no es válida"
        );
    }

    String tokenHash =
            calcularSha256(token);

    SesionDispositivo sesion =
            sesionRepository
                    .findByTokenHashAndRevocadaFalse(
                            tokenHash
                    )
                    .orElseThrow(
                            () ->
                                    new IllegalArgumentException(
                                            "La sesión no es válida"
                                    )
                    );

    OffsetDateTime ahora =
            OffsetDateTime.now(ZoneOffset.UTC);

    if (!sesion
            .getFechaExpiracion()
            .isAfter(ahora)) {

        sesion.setRevocada(true);
        sesion.setFechaCierre(ahora);

        sesionRepository.save(sesion);

        throw new IllegalArgumentException(
                "La sesión ha expirado"
        );
    }

    return sesion;
}
}