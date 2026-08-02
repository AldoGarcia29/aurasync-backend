package com.aurasync.aura.entity;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sesiones_dispositivo")
public class SesionDispositivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dispositivo_id", nullable = false)
    private Long dispositivoId;

    @Column(
            name = "token_hash",
            nullable = false,
            unique = true,
            length = 64
    )
    private String tokenHash;

    @Column(name = "fecha_inicio", nullable = false)
    private OffsetDateTime fechaInicio;

    @Column(
            name = "fecha_expiracion",
            nullable = false
    )
    private OffsetDateTime fechaExpiracion;

    @Column(nullable = false)
    private boolean revocada;

    @Column(name = "fecha_cierre")
    private OffsetDateTime fechaCierre;

    public SesionDispositivo() {
    }

    public Long getId() {
        return id;
    }

    public Long getDispositivoId() {
        return dispositivoId;
    }

    public void setDispositivoId(
            Long dispositivoId
    ) {
        this.dispositivoId = dispositivoId;
    }

    public String getTokenHash() {
        return tokenHash;
    }

    public void setTokenHash(String tokenHash) {
        this.tokenHash = tokenHash;
    }

    public OffsetDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(
            OffsetDateTime fechaInicio
    ) {
        this.fechaInicio = fechaInicio;
    }

    public OffsetDateTime getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(
            OffsetDateTime fechaExpiracion
    ) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public boolean isRevocada() {
        return revocada;
    }

    public void setRevocada(boolean revocada) {
        this.revocada = revocada;
    }

    public OffsetDateTime getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(
            OffsetDateTime fechaCierre
    ) {
        this.fechaCierre = fechaCierre;
    }
}