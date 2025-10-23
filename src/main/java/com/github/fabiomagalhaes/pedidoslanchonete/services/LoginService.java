package com.github.fabiomagalhaes.pedidoslanchonete.services;

import com.github.fabiomagalhaes.pedidoslanchonete.database.DatabaseH2;
import com.github.fabiomagalhaes.pedidoslanchonete.entities.LoggedUser;

import static com.github.fabiomagalhaes.pedidoslanchonete.Launcher.getMainDB;
import static com.github.fabiomagalhaes.pedidoslanchonete.Launcher.setUser;

public class LoginService {

    public void autenticar(String nome, String senha) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("O campo de primeiro nome não pode estar vazio!");
        }

        if (senha == null || senha.isEmpty()) {
            throw new IllegalArgumentException("O campo de senha não pode estar vazio!");
        }

        LoggedUser user = new DatabaseH2().checkUserAndPass(nome, senha);
        if (user == null) {
            throw new IllegalArgumentException("Usuário ou senha inválidos!");
        }

        setUser(user);
    }
}

