package com.github.fabiomagalhaes.pedidoslanchonete.controllers;

import com.github.fabiomagalhaes.pedidoslanchonete.entities.LoggedUser;
import com.github.fabiomagalhaes.pedidoslanchonete.views.Registration;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static com.github.fabiomagalhaes.pedidoslanchonete.Launcher.*;

public class LoginController {

    public TextField txtPrimeiroNome;
    public PasswordField txtSenha;

    public void btnFecharAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void btnLoginAction(ActionEvent actionEvent) {
        LoginAction();
    }

    public void onEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)){
            LoginAction();
        }
    }

    private void LoginAction() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Login no Sistema");

        if (txtPrimeiroNome.getText().isEmpty()){
            txtPrimeiroNome.requestFocus();

            alert.setContentText("O campo de primeiro nome não pode estar vazio!");
            alert.showAndWait();
            return;
        }

        if (txtSenha.getText().isEmpty()){
            txtSenha.requestFocus();

            alert.setContentText("O campo de senha não pode estar vazio!");
            alert.showAndWait();
            return;
        }

        LoggedUser user = getMainDB().checkUserAndPass(txtPrimeiroNome.getText(), txtSenha.getText());
        if (user == null){
            alert.setContentText("Usuário ou senha inválidos!");
            alert.showAndWait();
            return;
        }

        setUser(user);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Registration.class.getResource("main.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, root.prefWidth(-1), root.prefHeight(-1));
            Stage stage = new Stage();
            stage.setTitle("Seja Bem Vindo(a)");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            Stage wStage = (Stage) txtPrimeiroNome.getScene().getWindow();
            wStage.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void mouseClick(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Registration.class.getResource("register.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, root.prefWidth(-1), root.prefHeight(-1));
            Stage stage = new Stage();
            stage.setTitle("Registro de novo usuário");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            Stage wStage = (Stage) txtPrimeiroNome.getScene().getWindow();
            wStage.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
