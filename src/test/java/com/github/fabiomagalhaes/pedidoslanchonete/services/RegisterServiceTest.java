package com.github.fabiomagalhaes.pedidoslanchonete.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterServiceTest {
    private final RegisterService registerService = new RegisterService();

    @Test
    void registerCampoNome() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            registerService.registerUser("", "Fabio", "1234", "1234", false);
        });
        assertEquals("O campo de primeiro nome não pode estar vazio!", e.getMessage());
    }

    @Test
    void registerCampoSobrenome() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            registerService.registerUser("Fabio", "", "1234", "1234", false);
        });
        assertEquals("O campo de segundo nome não pode estar vazio!", e.getMessage());
    }

    @Test
    void registerCampoSenha1() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            registerService.registerUser("Fabio", "Fabio", "", "1234", false);
        });
        assertEquals("O campo de senha não pode estar vazio!", e.getMessage());
    }

    @Test
    void registerCampoSenha2() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            registerService.registerUser("Fabio", "Fabio", "1234", "", false);
        });
        assertEquals("O campo para repetir a senha não pode estar vazio!", e.getMessage());
    }
}