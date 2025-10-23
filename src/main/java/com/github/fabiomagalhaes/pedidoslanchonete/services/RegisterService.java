package com.github.fabiomagalhaes.pedidoslanchonete.services;

import com.github.fabiomagalhaes.pedidoslanchonete.database.DatabaseH2;
import com.github.fabiomagalhaes.pedidoslanchonete.entities.LoggedUser;

import static com.github.fabiomagalhaes.pedidoslanchonete.Launcher.getMainDB;
import static com.github.fabiomagalhaes.pedidoslanchonete.Launcher.setUser;

public class RegisterService
{
    public void registerUser(String nome, String sobreNome, String senha, String senha2, boolean adm){
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("O campo de primeiro nome não pode estar vazio!");
        }

        if (sobreNome == null || sobreNome.isEmpty()) {
            throw new IllegalArgumentException("O campo de segundo nome não pode estar vazio!");
        }

        if (senha == null || senha.isEmpty()) {
            throw new IllegalArgumentException("O campo de senha não pode estar vazio!");
        }

        if (senha2 == null || senha2.isEmpty()) {
            throw new IllegalArgumentException("O campo para repetir a senha não pode estar vazio!");
        }

        try {
            LoggedUser user = new DatabaseH2().insertUser(nome, sobreNome, senha, adm);
            setUser(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
