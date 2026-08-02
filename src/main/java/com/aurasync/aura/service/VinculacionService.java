package com.aurasync.aura.service;

import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aurasync.aura.dto.GenerarCodigoResponse;
import com.aurasync.aura.entity.CodigoVinculacion;
import com.aurasync.aura.repository.CodigoVinculacionRepository;
import com.aurasync.aura.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import com.aurasync.aura.dto.ConfirmarVinculacionRequest;
import com.aurasync.aura.dto.ConfirmarVinculacionResponse;
import com.aurasync.aura.entity.Dispositivo;
import com.aurasync.aura.repository.DispositivoRepository;



@Service
public class VinculacionService {

    private static final int DURACION_MINUTOS = 5;

    private final CodigoVinculacionRepository codigoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecureRandom secureRandom;
    private final DispositivoRepository dispositivoRepository;
    private final BiometriaService biometriaService;

    public VinculacionService(
        CodigoVinculacionRepository codigoRepository,
        UsuarioRepository usuarioRepository,
        DispositivoRepository dispositivoRepository,
        PasswordEncoder passwordEncoder,
        BiometriaService biometriaService
) {
    this.codigoRepository = codigoRepository;
    this.usuarioRepository = usuarioRepository;
    this.dispositivoRepository = dispositivoRepository;
    this.passwordEncoder = passwordEncoder;
    this.biometriaService = biometriaService;
    this.secureRandom = new SecureRandom();
}

    @Transactional
    public GenerarCodigoResponse generarCodigo(
            Long usuarioId
    ) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new IllegalArgumentException(
                    "El usuario no existe"
            );
        }

        invalidarCodigosAnteriores(usuarioId);

        String codigo = String.format(
                "%06d",
                secureRandom.nextInt(1_000_000)
        );

        OffsetDateTime ahora =
                OffsetDateTime.now(ZoneOffset.UTC);

        OffsetDateTime expiracion =
                ahora.plusMinutes(DURACION_MINUTOS);

        CodigoVinculacion registro =
                new CodigoVinculacion();

        registro.setUsuarioId(usuarioId);

        registro.setCodigoHash(
                passwordEncoder.encode(codigo)
        );

        registro.setFechaCreacion(ahora);
        registro.setFechaExpiracion(expiracion);
        registro.setUtilizado(false);

        codigoRepository.save(registro);

        return new GenerarCodigoResponse(
                codigo,
                expiracion,
                DURACION_MINUTOS,
                "Código temporal generado correctamente"
        );
    }


    @Transactional
public ConfirmarVinculacionResponse
confirmarVinculacion(
        ConfirmarVinculacionRequest request
) {
    OffsetDateTime ahora =
            OffsetDateTime.now(ZoneOffset.UTC);

    List<CodigoVinculacion> codigosVigentes =
            codigoRepository
                    .findByUtilizadoFalseAndFechaExpiracionAfterOrderByFechaCreacionDesc(
                            ahora
                    );

    Optional<CodigoVinculacion> coincidencia =
            codigosVigentes
                    .stream()
                    .filter(codigo ->
                            passwordEncoder.matches(
                                    request.codigo(),
                                    codigo.getCodigoHash()
                            )
                    )
                    .findFirst();

    if (coincidencia.isEmpty()) {
        throw new IllegalArgumentException(
                "El código es incorrecto o ha expirado"
        );
    }

    CodigoVinculacion codigo =
            coincidencia.get();

    codigo.setUtilizado(true);
    codigoRepository.save(codigo);

    Dispositivo dispositivo = new Dispositivo();

    dispositivo.setUsuarioId(
            codigo.getUsuarioId()
    );

    dispositivo.setTipo("Smartwatch");

    dispositivo.setNombre(
            request.nombreDispositivo().trim()
    );

    dispositivo.setEstado("Vinculado");

    dispositivo.setFechaConexion(
            LocalDateTime.now(ZoneOffset.UTC)
    );

    dispositivo.setPinHash(
        passwordEncoder.encode(request.pin())
);

dispositivo.setPinHabilitado(true);
    dispositivo.setIntentosFallidos(0);
    dispositivo.setBloqueadoHasta(null);
    dispositivo.setUltimaAutenticacion(null);

    Dispositivo guardado =
        dispositivoRepository.save(dispositivo);

biometriaService.simularLectura(
        guardado.getUsuarioId()
);

return new ConfirmarVinculacionResponse(
        true,
        guardado.getId(),
        guardado.getUsuarioId(),
        guardado.getNombre(),
        false,
        "Smartwatch vinculado correctamente"
);
}

    private void invalidarCodigosAnteriores(
            Long usuarioId
    ) {
        List<CodigoVinculacion> codigosActivos =
                codigoRepository
                        .findByUsuarioIdAndUtilizadoFalse(
                                usuarioId
                        );

        for (CodigoVinculacion codigo
                : codigosActivos) {
            codigo.setUtilizado(true);
        }

        if (!codigosActivos.isEmpty()) {
            codigoRepository.saveAll(codigosActivos);
        }
    }
}