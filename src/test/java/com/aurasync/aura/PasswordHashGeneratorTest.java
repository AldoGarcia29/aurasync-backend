package com.aurasync.aura;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class PasswordHashGeneratorTest {

    @Test
    void generarHash() {
        PasswordEncoder encoder =
                new BCryptPasswordEncoder(12);

        String passwordTemporal = "AuraSync123";

        String hash = encoder.encode(passwordTemporal);

        System.out.println();
        System.out.println("HASH BCRYPT GENERADO:");
        System.out.println(hash);
        System.out.println();

        boolean valido = encoder.matches(
                passwordTemporal,
                hash
        );

        System.out.println(
                "¿EL HASH ES VALIDO?: " + valido
        );
    }
}