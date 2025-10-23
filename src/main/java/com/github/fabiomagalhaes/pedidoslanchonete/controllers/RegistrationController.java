package com.github.fabiomagalhaes.pedidoslanchonete.controllers;

import com.github.fabiomagalhaes.pedidoslanchonete.entities.LoggedUser;
import com.github.fabiomagalhaes.pedidoslanchonete.services.RegisterService;
import com.github.fabiomagalhaes.pedidoslanchonete.views.Registration;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static com.github.fabiomagalhaes.pedidoslanchonete.Launcher.*;

public class RegistrationController {

    public TextField txtPrimeiroNome;
    public TextField txtSegundoNome;
    public PasswordField txtSenha;
    public PasswordField txtSenha2;
    public Label lblAskLogin;

    public void btnRegistrarAction(ActionEvent actionEvent) {
        RegisterAction();
    }

    public void btnFecharAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    private boolean adm;
    public void isUserAdm(boolean _adm){
        adm = _adm;
        lblAskLogin.setVisible(!_adm);
    }

    public void onClick(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Registration.class.getResource("login.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, root.prefWidth(-1), root.prefHeight(-1));
            Stage stage = new Stage();
            stage.setTitle("Login no Sistema");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            Stage wStage = (Stage) txtPrimeiroNome.getScene().getWindow();
            wStage.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)){
            RegisterAction();
        }
    }

    private void RegisterAction() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Registro de Usuário");

        try {
            RegisterService regService = new RegisterService();
            regService.registerUser(txtPrimeiroNome.getText(), txtSegundoNome.getText(), txtSenha.getText(), txtSenha2.getText(), adm);

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
        } catch (Exception e) {
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
