package Controller;

import Estructuras.ListaCircular.ListaCircular;
import Model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class registroComponentController {
    @FXML
    Pane mainPane;
    @FXML
    TextField emailField;
    @FXML
    TextField userField;
    @FXML
    PasswordField pwdField;
    @FXML
    PasswordField pwdConField;
    @FXML
    Label errorUserLabel;
    @FXML
    Label errorPass;
    private Tienda tienda = Tienda.getInstance();
    public void registrar(){
        if(tienda.existeUsuario(userField.getText())){
            errorUserLabel.setText("Este usuario ya está registrado");
        }
        else if(!pwdField.getText().equals(pwdConField.getText())){
            errorPass.setText("Las contraseñas no coinciden");
        }

        if(!tienda.existeUsuario(userField.getText()) && pwdField.getText().equals(pwdConField.getText())){
            Usuario userNew = new Usuario(userField.getText(),pwdField.getText(), emailField.getText(),new ListaCircular<>());
            tienda.agregarUsuarios(userNew);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registro de Usuario");
            alert.setContentText("Se ha registrado correctamente");
            alert.show();
            cambiarComponente();
        }
    }
    public void cambiarEstadoPwd(){
        errorPass.setText("");
    }
    public void cambiarEstadoUser(){
        errorUserLabel.setText("");
    }
    public void cambiarComponente(){
        HBox stage = (HBox) mainPane.getParent();
        try {
            // Cargar el FXML del componente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/loginComponent.fxml"));
            Pane registro = loader.load();

            // Agregar el componente al HBox
            stage.getChildren().set(1,registro );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
