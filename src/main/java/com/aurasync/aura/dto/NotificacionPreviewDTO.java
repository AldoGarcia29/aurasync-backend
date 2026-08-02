package com.aurasync.aura.dto;

import java.time.LocalDateTime;

public class NotificacionPreviewDTO {

    private Long id;
    private String titulo;
    private String mensajeCorto;
    private String prioridad;
    private LocalDateTime fecha;

    public NotificacionPreviewDTO(
            Long id,
            String prioridad,
            LocalDateTime fecha
    ) {
        this.id = id;
        this.titulo = "AuraSync";
        this.mensajeCorto =
                "Nueva actualización de bienestar";
        this.prioridad = prioridad;
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensajeCorto() {
        return mensajeCorto;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
}