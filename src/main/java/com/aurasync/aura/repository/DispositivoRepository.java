package com.aurasync.aura.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aurasync.aura.entity.Dispositivo;

@Repository
public interface DispositivoRepository
        extends JpaRepository<Dispositivo, Long> {

    List<Dispositivo>
    findByUsuarioIdOrderByFechaConexionDesc(
            Long usuarioId
    );
}