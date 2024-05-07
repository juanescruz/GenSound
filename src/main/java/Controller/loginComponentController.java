package Controller;

import Model.Tienda;
import Model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.*;

public class loginComponentController {
    @FXML
    Pane PaneMain;
    @FXML
    Pane paneRight;
    @FXML
    TextField usuarioField;
    @FXML
    PasswordField pwdField;
    @FXML
    Button iniciarBoton;
    @FXML
    Button registrarBoton;
    @FXML
    Label usuarioError;
    @FXML
    Label pwdError;
    private  Tienda tienda = Tienda.getInstance();

    public void iniciarSesion(){
        if(tienda.existeUsuario(usuarioField.getText())){
            if(tienda.contrasenaCorrecta(usuarioField.getText(), pwdField.getText())){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Info");
                alert.setContentText("Inicio de sesión correcto");
                alert.show();
            }
            else{
                pwdError.setText("Contraseña Incorrecta");
            }
        }
        else{
            usuarioError.setText("Usuario no está registrado");
        }
    }
    public void irARegistro(){
        HBox stage = (HBox) PaneMain.getParent();
        try {
            // Cargar el FXML del componente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/registroComponent.fxml"));
            Pane registro = loader.load();

            // Agregar el componente al HBox
            stage.getChildren().set(1,registro );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
