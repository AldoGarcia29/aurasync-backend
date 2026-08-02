package com.aurasync.aura.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "biometria")
public class Biometria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "ritmo_cardiaco")
    private Integer ritmoCardiaco;

    @Column
    private Integer pasos;

    @Column(name = "actividad_minutos")
    private Integer actividadMinutos;

    @Column(name = "horas_sueno")
    private BigDecimal horasSueno;

    @Column
    private LocalDateTime fecha;

    public Biometria() {
    }

    public Long getId() {
        return id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getRitmoCardiaco() {
        return ritmoCardiaco;
    }

    public void setRitmoCardiaco(
            Integer ritmoCardiaco
    ) {
        this.ritmoCardiaco = ritmoCardiaco;
    }

    public Integer getPasos() {
        return pasos;
    }

    public void setPasos(Integer pasos) {
        this.pasos = pasos;
    }

    public Integer getActividadMinutos() {
        return actividadMinutos;
    }

    public void setActividadMinutos(
            Integer actividadMinutos
    ) {
        this.actividadMinutos =
                actividadMinutos;
    }

    public BigDecimal getHorasSueno() {
        return horasSueno;
    }

    public void setHorasSueno(
            BigDecimal horasSueno
    ) {
        this.horasSueno = horasSueno;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}