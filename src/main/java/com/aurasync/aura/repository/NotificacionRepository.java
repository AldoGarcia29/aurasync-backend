package com.aurasync.aura.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aurasync.aura.entity.Notificacion;

@Repository
public interface NotificacionRepository
        extends JpaRepository<Notificacion, Long> {

    List<Notificacion>
    findByUsuarioIdAndLeidaFalseOrderByFechaDesc(
            Long usuarioId
    );

    Optional<Notificacion>
    findByIdAndUsuarioId(
            Long id,
            Long usuarioId
    );
}