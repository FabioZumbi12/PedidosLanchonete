package com.github.fabiomagalhaes.pedidoslanchonete.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Login extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("login.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, root.prefWidth(-1), root.prefHeight(-1));
        stage.setTitle("Login no Sistema");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
