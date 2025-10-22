package com.github.fabiomagalhaes.pedidoslanchonete;

import com.github.fabiomagalhaes.pedidoslanchonete.database.DatabaseH2;
import com.github.fabiomagalhaes.pedidoslanchonete.database.IDatabase;
import com.github.fabiomagalhaes.pedidoslanchonete.entities.LoggedUser;
import com.github.fabiomagalhaes.pedidoslanchonete.views.Login;
import com.github.fabiomagalhaes.pedidoslanchonete.views.Main;
import com.github.fabiomagalhaes.pedidoslanchonete.views.Registration;
import javafx.application.Application;

import static com.github.fabiomagalhaes.pedidoslanchonete.util.Helper.*;

public class Launcher {
    private static IDatabase mainDB;
    public static IDatabase getMainDB() {
        return mainDB;
    };

    private static LoggedUser user;
    public static LoggedUser getUser() { return user; }
    public static void setUser(LoggedUser value) { user = value; }

    static void main(String[] args) {

        setupDollarRate();
        mainDB = new DatabaseH2();

        if (!mainDB.hasUsers())
            Application.launch(Registration.class, "--adm=true");

        else if (args.length > 0 && args[0].equals("--dev")) {
            user = mainDB.getUser("1");
            Application.launch(Main.class, args);
        }
        else
            Application.launch(Login.class, args);
    }
}
