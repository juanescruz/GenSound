

package Controller;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class ReproductorController {
    @FXML
    WebView reproductor;
    @FXML
    Label lblCargando;
    private boolean cargado = false;
    private boolean reproduciendo = false;
    private String urlCancion;
    private boolean pausado=true;
    private InicioUsuarioController inicioUsuarioController;

    /**
     * Método para reproducir el video.
     */
    public void reproducir(){
        if (reproduciendo|| pausado){
        cargarVideo();
        reproductor.setVisible(false);
        reproduciendo=true;
    }
        if (cargado) {
        WebEngine webEngine = reproductor.getEngine();
        webEngine.executeScript("var player = document.querySelector('.html5-main-video'); " +
                "if (player) { player.play(); }");
        reproduciendo = true;
    } else {
        cargarVideo();
        reproductor.setVisible(false);
        reproduciendo = true;
    }
}
    /**
     * Método privado para cargar un video en el reproductor.
     */
    private void cargarVideo() {
        WebEngine webEngine = reproductor.getEngine();
        lblCargando.setText("Cargando...");
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                cargado = true;
                // Reproducir el video después de que se haya cargado
                lblCargando.setText("");
                webEngine.executeScript("var player = document.querySelector('.html5-main-video'); " +
                        "if (player) { player.play(); }");
            }
        });
        // Cargar la página de YouTube
        webEngine.load(urlCancion);
    }
    /**
     * Método para pausar la reproducción del video.
     */
    public void pausar() {
        if (!reproduciendo) {
            // Si ya estaba pausado, no hacemos nada
            return;
        }
        WebEngine webEngine = reproductor.getEngine();
        webEngine.executeScript("var player = document.querySelector('.html5-main-video'); " +
                "if (player) { player.pause(); }");
        reproduciendo = false;
        pausado=true;
    }
    /**
     * Método para establecer la URL de la canción y reproducir el video correspondiente.
     * @param url La URL de la canción a establecer.
     */
    public void setURLCancion(String url){
        this.urlCancion= url;
        reproducir();
    }

    public void setInicioUsuarioController(InicioUsuarioController inicioUsuarioController) {
        this.inicioUsuarioController = inicioUsuarioController;
    }
}
