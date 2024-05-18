package Controller;

import Model.InicioSesion;
import Model.Usuario;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;
import java.io.IOException;


public class PopUpDeshacerController {

    @FXML
    public Label MensajeLabel;
    @FXML
    private Button BotonDeshacer;

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

    @FXML
    public void OnDeshacerClick(ActionEvent actionEvent) throws IOException {

        File url = new File("src/main/resources/View/PopUpRehacer.fxml");
        FXMLLoader loader = new FXMLLoader(url.toURL());
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        scene.setFill(Color.TRANSPARENT);
        stage.show();

        Stage stage1 = (Stage) MensajeLabel.getScene().getWindow();
        stage1.close();

        Usuario usuario = InicioSesion.getInstance().getUsuario();
        usuario.getCancionesFav().deshacer();

    }

}
