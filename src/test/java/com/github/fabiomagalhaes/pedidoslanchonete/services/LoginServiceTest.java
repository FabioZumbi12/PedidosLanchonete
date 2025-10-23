package com.github.fabiomagalhaes.pedidoslanchonete.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {

    LoginService loginService = new LoginService();

    @Test
    void loginCampoSobrenome() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            loginService.autenticar("", "1122");
        });
        assertEquals("O campo de primeiro nome não pode estar vazio!", e.getMessage());
    }

    @Test
    void loginCampoSenha() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            loginService.autenticar("Fabio", "");
        });
        assertEquals("O campo de senha não pode estar vazio!", e.getMessage());
    }

    @Test
    void loginFail() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            loginService.autenticar("Fabio", "1122");
        });
        assertEquals("Usuário ou senha inválidos!", e.getMessage());
    }
}