package Controller;

import Model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.Getter;

public class RegistroComponentController {
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
    private void registrar(){
        if(tienda.existeUsuario(userField.getText())){
            errorUserLabel.setText("Este usuario ya está registrado");
        }
        else if(!pwdField.getText().equals(pwdConField.getText())){
            errorPass.setText("Las contraseñas no coinciden");
        }

        if(!tienda.existeUsuario(userField.getText()) && pwdField.getText().equals(pwdConField.getText())){

        }
    }
}
