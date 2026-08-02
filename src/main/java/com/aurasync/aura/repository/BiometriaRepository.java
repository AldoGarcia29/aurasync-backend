package com.aurasync.aura.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aurasync.aura.entity.Biometria;

@Repository
public interface BiometriaRepository
        extends JpaRepository<Biometria, Long> {

    Optional<Biometria> findTopByUsuarioIdOrderByFechaDesc(
            Long usuarioId
    );

    List<Biometria> findByUsuarioIdOrderByFechaAsc(
            Long usuarioId
    );
}