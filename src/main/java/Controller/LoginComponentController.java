package Controller;

import App.MainApp;
import Model.InicioSesion;
import Model.Tienda;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginComponentController {
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
    private final InicioSesion inicioSesion= InicioSesion.getInstance();

    /**
     * Método para iniciar sesión en la tienda.
     * @throws IOException Si ocurre un error durante la carga de la vista correspondiente.
     */
    public void iniciarSesion() throws IOException {
        if(tienda.existeUsuario(usuarioField.getText())){
            if(tienda.contrasenaCorrecta(usuarioField.getText(), pwdField.getText())){
                inicioSesion.setUsuario(tienda.getUser(usuarioField.getText()));
                System.out.println(inicioSesion.getUsuario());
                FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/View/InicioUsuario.fxml"));
                extracted(loader);
                Stage anterior = (Stage) usuarioError.getParent().getParent().getScene().getWindow();
                anterior.close();
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
            if(usuarioField.getText().equals("admin") && pwdField.getText().equals("$aDmiN")){
                FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/View/InicioAdmin.fxml"));
                extracted(loader);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Info");
                alert.setContentText("Sesión de administrador iniciada");
                Stage anterior = (Stage) usuarioError.getParent().getParent().getScene().getWindow();
                anterior.close();
                alert.show();
            }else {
                usuarioError.setText("Usuario no está registrado");
            }

        }
    }

    private static void extracted(FXMLLoader loader) throws IOException {
        Parent parent = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Gensound");
        stage.show();
    }

    /**
     * Método para redirigir a la ventana de registro.
     */
    public void irARegistro(){
        BorderPane stage = (BorderPane) usuarioError.getParent().getParent();
        try {
            // Cargar el FXML del componente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/registroComponent.fxml"));
            Pane registro = loader.load();

            // Agregar el componente al HBox
            stage.setCenter(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
