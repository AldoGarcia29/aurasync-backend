package com.aurasync.aura.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.aurasync.aura.entity.Biometria;
import com.aurasync.aura.entity.Notificacion;
import com.aurasync.aura.repository.NotificacionRepository;
import java.util.List;

import com.aurasync.aura.dto.NotificacionPreviewDTO;

@Service
public class NotificacionService {

    private final NotificacionRepository repository;

    public NotificacionService(
            NotificacionRepository repository
    ) {
        this.repository = repository;
    }

    public Notificacion crearDesdeBiometria(
            Biometria biometria
    ) {
        Notificacion notificacion =
                new Notificacion();

        notificacion.setUsuarioId(
                biometria.getUsuarioId()
        );

        notificacion.setBiometriaId(
                biometria.getId()
        );

        notificacion.setLeida(false);
        notificacion.setFecha(LocalDateTime.now());

        if (biometria.getRitmoCardiaco() > 100) {
            notificacion.setTitulo(
                    "Ritmo cardiaco elevado"
            );
            notificacion.setPrioridad("ALTA");
            notificacion.setMensaje(
                    "Se detectó un ritmo cardiaco de "
                    + biometria.getRitmoCardiaco()
                    + " lpm. Realiza una pausa y practica "
                    + "respiración lenta."
            );
        } else if (
                biometria.getHorasSueno().doubleValue() < 6
        ) {
            notificacion.setTitulo(
                    "Descanso insuficiente"
            );
            notificacion.setPrioridad("ALTA");
            notificacion.setMensaje(
                    "Registraste "
                    + biometria.getHorasSueno()
                    + " horas de sueño. Procura descansar "
                    + "y reducir actividades intensas."
            );
        } else if (biometria.getPasos() < 3000) {
            notificacion.setTitulo(
                    "Actividad baja"
            );
            notificacion.setPrioridad("MEDIA");
            notificacion.setMensaje(
                    "Registraste "
                    + biometria.getPasos()
                    + " pasos. Una caminata corta puede "
                    + "ayudarte a mejorar tu energía."
            );
        } else {
            notificacion.setTitulo(
                    "Resumen de bienestar"
            );
            notificacion.setPrioridad("BAJA");
            notificacion.setMensaje(
                    "Tus indicadores se encuentran "
                    + "estables. Mantén buenos hábitos "
                    + "de descanso y actividad."
            );
        }

        return repository.save(notificacion);
    }

    public List<NotificacionPreviewDTO> obtenerPendientes(
        Long usuarioId
) {
    return repository
            .findByUsuarioIdAndLeidaFalseOrderByFechaDesc(
                    usuarioId
            )
            .stream()
            .map(
                    notificacion ->
                            new NotificacionPreviewDTO(
                                    notificacion.getId(),
                                    notificacion.getPrioridad(),
                                    notificacion.getFecha()
                            )
            )
            .toList();
}
}