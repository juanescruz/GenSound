package Controller;

import Model.InicioSesion;
import Model.Usuario;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PopUpRehacerController {
    @FXML
    public Label MensajeLabel;
    @FXML
    public Button BotonDeshacer;

    public void initialize() {

        new Thread(() -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {
                Stage stage = (Stage) MensajeLabel.getScene().getWindow();
                stage.close();
            });
        }).start();
    }

    public void OnRehacerClick(ActionEvent actionEvent) {

        Stage stage = (Stage) MensajeLabel.getScene().getWindow();
        stage.close();

        Usuario usuario = InicioSesion.getInstance().getUsuario();
        usuario.getCancionesFav().rehacer();

    }
}
