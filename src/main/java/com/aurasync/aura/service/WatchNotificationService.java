package com.aurasync.aura.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aurasync.aura.dto.NotificacionDetalleResponse;
import com.aurasync.aura.entity.Biometria;
import com.aurasync.aura.entity.Dispositivo;
import com.aurasync.aura.entity.Notificacion;
import com.aurasync.aura.entity.SesionDispositivo;
import com.aurasync.aura.repository.BiometriaRepository;
import com.aurasync.aura.repository.DispositivoRepository;
import com.aurasync.aura.repository.NotificacionRepository;

@Service
public class WatchNotificationService {

    private final WatchAuthService watchAuthService;
    private final DispositivoRepository dispositivoRepository;
    private final NotificacionRepository notificacionRepository;
    private final BiometriaRepository biometriaRepository;

    public WatchNotificationService(
            WatchAuthService watchAuthService,
            DispositivoRepository dispositivoRepository,
            NotificacionRepository notificacionRepository,
            BiometriaRepository biometriaRepository
    ) {
        this.watchAuthService = watchAuthService;
        this.dispositivoRepository =
                dispositivoRepository;
        this.notificacionRepository =
                notificacionRepository;
        this.biometriaRepository =
                biometriaRepository;
    }

    @Transactional
    public NotificacionDetalleResponse desbloquear(
            Long notificacionId,
            String token
    ) {
        SesionDispositivo sesion =
                watchAuthService.validarSesion(token);

        Dispositivo dispositivo =
                dispositivoRepository
                        .findById(
                                sesion.getDispositivoId()
                        )
                        .orElseThrow(
                                () -> new IllegalArgumentException(
                                        "El dispositivo no existe"
                                )
                        );

        Notificacion notificacion =
                notificacionRepository
                        .findByIdAndUsuarioId(
                                notificacionId,
                                dispositivo.getUsuarioId()
                        )
                        .orElseThrow(
                                () -> new IllegalArgumentException(
                                        "La notificación no existe "
                                        + "o no pertenece al usuario"
                                )
                        );

        if (notificacion.getBiometriaId() == null) {
            throw new IllegalArgumentException(
                    "La notificación no tiene "
                    + "información biométrica relacionada"
            );
        }

        Biometria biometria =
                biometriaRepository
                        .findById(
                                notificacion.getBiometriaId()
                        )
                        .orElseThrow(
                                () -> new IllegalArgumentException(
                                        "La lectura biométrica no existe"
                                )
                        );

        notificacion.setLeida(true);

        notificacionRepository.save(notificacion);

        return new NotificacionDetalleResponse(
                notificacion.getId(),
                notificacion.getTitulo(),
                notificacion.getMensaje(),
                notificacion.getPrioridad(),
                biometria.getRitmoCardiaco(),
                biometria.getPasos(),
                biometria.getActividadMinutos(),
                biometria.getHorasSueno(),
                biometria.getFecha()
        );
    }
}