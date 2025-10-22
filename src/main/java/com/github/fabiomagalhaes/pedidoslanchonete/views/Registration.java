package com.github.fabiomagalhaes.pedidoslanchonete.views;

import com.github.fabiomagalhaes.pedidoslanchonete.controllers.RegistrationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class Registration extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Map<String, String> named = getParameters().getNamed();
        boolean adm = named.containsKey("adm");

        FXMLLoader fxmlLoader = new FXMLLoader(Registration.class.getResource("register.fxml"));
        Parent root = fxmlLoader.load();

        RegistrationController controller = fxmlLoader.getController();
        controller.isUserAdm(adm);

        Scene scene = new Scene(root, root.prefWidth(-1), root.prefHeight(-1));
        stage.setTitle("Nenhum usuário encontrato - Registre um usuário ADMINISTRADOR");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
