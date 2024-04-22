package Controller;

import Model.Tienda;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class loginComponentController {
    @FXML
    AnchorPane anchorPaneMain;
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

    private  Tienda tienda = Tienda.getInstance();

    public void iniciarSesion(){
        if(tienda.existeUsuario(usuarioField.getText())){
            if(tienda.contrasenaCorrecta(usuarioField.getText(), pwdField.getText())){
                System.out.println("correcto");
            }
            else{
                System.out.println("psw incorrecta");
            }
        }
        else{
            System.out.println("No esta registrado");
        }
    }
    public void irARegistro(){
        
    }



}
