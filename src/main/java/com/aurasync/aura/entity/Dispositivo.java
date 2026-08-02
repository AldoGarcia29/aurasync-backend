package com.aurasync.aura.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "dispositivos")
public class Dispositivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String estado;

    @Column(name = "fecha_conexion")
    private LocalDateTime fechaConexion;

    @Column(name = "pin_hash")
    private String pinHash;

    @Column(name = "pin_habilitado", nullable = false)
    private boolean pinHabilitado;

    @Column(
            name = "intentos_fallidos",
            nullable = false
    )
    private int intentosFallidos;

    @Column(name = "bloqueado_hasta")
    private LocalDateTime bloqueadoHasta;

    @Column(name = "ultima_autenticacion")
    private LocalDateTime ultimaAutenticacion;

    public Dispositivo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaConexion() {
        return fechaConexion;
    }

    public void setFechaConexion(
            LocalDateTime fechaConexion
    ) {
        this.fechaConexion = fechaConexion;
    }

    public String getPinHash() {
        return pinHash;
    }

    public void setPinHash(String pinHash) {
        this.pinHash = pinHash;
    }

    public boolean isPinHabilitado() {
        return pinHabilitado;
    }

    public void setPinHabilitado(
            boolean pinHabilitado
    ) {
        this.pinHabilitado = pinHabilitado;
    }

    public int getIntentosFallidos() {
        return intentosFallidos;
    }

    public void setIntentosFallidos(
            int intentosFallidos
    ) {
        this.intentosFallidos = intentosFallidos;
    }

    public LocalDateTime getBloqueadoHasta() {
        return bloqueadoHasta;
    }

    public void setBloqueadoHasta(
            LocalDateTime bloqueadoHasta
    ) {
        this.bloqueadoHasta = bloqueadoHasta;
    }

    public LocalDateTime getUltimaAutenticacion() {
        return ultimaAutenticacion;
    }

    public void setUltimaAutenticacion(
            LocalDateTime ultimaAutenticacion
    ) {
        this.ultimaAutenticacion =
                ultimaAutenticacion;
    }
}