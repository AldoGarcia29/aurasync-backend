package com.aurasync.aura.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class NotificacionDetalleResponse {

    private final Long id;
    private final String titulo;
    private final String mensaje;
    private final String prioridad;
    private final Integer ritmoCardiaco;
    private final Integer pasos;
    private final Integer actividadMinutos;
    private final BigDecimal horasSueno;
    private final LocalDateTime fecha;

    public NotificacionDetalleResponse(
            Long id,
            String titulo,
            String mensaje,
            String prioridad,
            Integer ritmoCardiaco,
            Integer pasos,
            Integer actividadMinutos,
            BigDecimal horasSueno,
            LocalDateTime fecha
    ) {
        this.id = id;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.prioridad = prioridad;
        this.ritmoCardiaco = ritmoCardiaco;
        this.pasos = pasos;
        this.actividadMinutos = actividadMinutos;
        this.horasSueno = horasSueno;
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public Integer getRitmoCardiaco() {
        return ritmoCardiaco;
    }

    public Integer getPasos() {
        return pasos;
    }

    public Integer getActividadMinutos() {
        return actividadMinutos;
    }

    public BigDecimal getHorasSueno() {
        return horasSueno;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
}
