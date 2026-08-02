package com.aurasync.aura.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "notificaciones")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "biometria_id")
    private Long biometriaId;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, columnDefinition = "text")
    private String mensaje;

    @Column(nullable = false)
    private String prioridad;

    @Column(nullable = false)
    private Boolean leida = false;

    @Column(nullable = false)
    private LocalDateTime fecha;

    public Notificacion() {
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

    public Long getBiometriaId() {
        return biometriaId;
    }

    public void setBiometriaId(Long biometriaId) {
        this.biometriaId = biometriaId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public Boolean getLeida() {
        return leida;
    }

    public void setLeida(Boolean leida) {
        this.leida = leida;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}