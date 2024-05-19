package Controller;

import Model.Cancion;
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

        InicioUsuarioController.getInstance().pintarPlaylist();

        System.out.println();
        System.out.println("Canciones actualizadas despues de la funcion rehacer: ");

        int contador = 0;

        for (Cancion cancion : InicioSesion.getInstance().getUsuario().getCancionesFav()) {

            if(contador == InicioSesion.getInstance().getUsuario().getCancionesFav().getTamanio()){
                break;
            } else {
                System.out.println(cancion.getNombreCancion());
            }
            contador++;
        }

    }
}
