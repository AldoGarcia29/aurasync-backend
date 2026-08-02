package com.aurasync.aura.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aurasync.aura.entity.Usuario;
import com.aurasync.aura.repository.UsuarioRepository;
import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> autenticar(
            String correo,
            String password
    ) {
        String correoNormalizado = correo.trim().toLowerCase();

        Optional<Usuario> usuarioEncontrado =
                usuarioRepository.findByCorreoIgnoreCase(
                        correoNormalizado
                );

        if (usuarioEncontrado.isEmpty()) {
            return Optional.empty();
        }

        Usuario usuario = usuarioEncontrado.get();

        boolean passwordCorrecto = passwordEncoder.matches(
                password,
                usuario.getPasswordHash()
        );

        if (!passwordCorrecto) {
            return Optional.empty();
        }

        return Optional.of(usuario);
    }

    @Transactional
public Usuario registrar(
        String nombre,
        String correo,
        String password
) {
    String nombreLimpio = nombre.trim();
    String correoNormalizado =
            correo.trim().toLowerCase();

    if (usuarioRepository
            .findByCorreoIgnoreCase(correoNormalizado)
            .isPresent()) {
        throw new IllegalArgumentException(
                "Ya existe una cuenta con ese correo."
        );
    }

    Usuario usuario = new Usuario();

    usuario.setNombre(nombreLimpio);
    usuario.setCorreo(correoNormalizado);
    usuario.setPasswordHash(
            passwordEncoder.encode(password)
    );
    usuario.setFechaRegistro(
            LocalDateTime.now()
    );

    return usuarioRepository.save(usuario);
}
}