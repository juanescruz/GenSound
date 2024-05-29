package Controller;

import App.MainApp;
import Model.Cancion;
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
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
@Setter
@Getter
public class PopUpDeshacerController {

    @FXML
    public Label MensajeLabel;
    @FXML
    private Button BotonDeshacer;
    private InicioUsuarioController inicioUsuarioController;
    private PopUpRehacerController rehacerController;

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

    /**
     * Método invocado al hacer clic en el botón de "Deshacer"
     * @param actionEvent El evento de acción que desencadenó este método.
     * @throws IOException Si ocurre un error durante la carga del archivo FXML del popup.
     */
    @FXML
    public void OnDeshacerClick(ActionEvent actionEvent) throws IOException {

        Stage stage1 = (Stage) MensajeLabel.getScene().getWindow();
        stage1.close();

        Usuario usuario = InicioSesion.getInstance().getUsuario();
        usuario.getCancionesFav().deshacer();

        inicioUsuarioController.pintarPlaylist();

//        mostrarInformacionDeLaLista();

        FXMLLoader loader = new FXMLLoader( MainApp.class.getResource("/View/PopUpRehacer.fxml") );
        Parent parent = loader.load();
        rehacerController = loader.getController();
        rehacerController.setInicioUsuarioController(inicioUsuarioController);
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        scene.setFill(Color.TRANSPARENT);

        Stage mainStage = (Stage) inicioUsuarioController.getBorderPane().getScene().getWindow();
        double mainStageX = mainStage.getX();
        double mainStageY = mainStage.getY();

        stage.setX(mainStageX + 10);
        stage.setY(mainStageY + 80);

        stage.show();

    }
    /**
     * Método estático para mostrar información sobre las canciones actualizadas después de la función deshacer.
     */
    private static void mostrarInformacionDeLaLista() {

        System.out.println();
        System.out.println("Canciones actualizadas despues de la funcion deshacer: ");

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
