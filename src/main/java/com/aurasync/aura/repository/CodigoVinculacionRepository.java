package com.aurasync.aura.repository;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aurasync.aura.entity.CodigoVinculacion;

@Repository
public interface CodigoVinculacionRepository
        extends JpaRepository<CodigoVinculacion, Long> {

    List<CodigoVinculacion>
    findByUsuarioIdAndUtilizadoFalse(
            Long usuarioId
    );

    List<CodigoVinculacion>
    findByUtilizadoFalseAndFechaExpiracionAfterOrderByFechaCreacionDesc(
            OffsetDateTime fechaActual
    );
}