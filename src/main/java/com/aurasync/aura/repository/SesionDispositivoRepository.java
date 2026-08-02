package com.aurasync.aura.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aurasync.aura.entity.SesionDispositivo;

@Repository
public interface SesionDispositivoRepository
        extends JpaRepository<SesionDispositivo, Long> {

    Optional<SesionDispositivo>
    findByTokenHashAndRevocadaFalse(
            String tokenHash
    );

    List<SesionDispositivo>
    findByDispositivoIdAndRevocadaFalse(
            Long dispositivoId
    );
}