package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class InicioSesionController implements Initializable {
    @FXML
    HBox HBoxMain;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Cargar el FXML del componente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/loginComponent.fxml"));
            Pane inicioSesion = loader.load();

            // Agregar el componente al HBox
            HBoxMain.getChildren().add(inicioSesion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
