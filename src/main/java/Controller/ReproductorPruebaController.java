

package Controller;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class ReproductorPruebaController{
    @FXML
    WebView reproductor;
    private boolean cargado = false;
    private boolean reproduciendo = false;
    private String urlCancion;

    private InicioUsuarioController inicioUsuarioController;

    public void reproducir(){
        if (reproduciendo){
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
    private void cargarVideo() {
        WebEngine webEngine = reproductor.getEngine();
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                cargado = true;
                // Reproducir el video después de que se haya cargado
                webEngine.executeScript("var player = document.querySelector('.html5-main-video'); " +
                        "if (player) { player.play(); }");
            }
        });
        // Cargar la página de YouTube
        webEngine.load(urlCancion);
    }
    public void pausar() {
        if (!reproduciendo) {
            // Si ya estaba pausado, no hacemos nada
            return;
        }
        WebEngine webEngine = reproductor.getEngine();
        webEngine.executeScript("var player = document.querySelector('.html5-main-video'); " +
                "if (player) { player.pause(); }");
        reproduciendo = false;
    }
    public void setURLCancion(String url){
        this.urlCancion= url;
        reproducir();
    }

    public void setInicioUsuarioController(InicioUsuarioController inicioUsuarioController) {
        this.inicioUsuarioController = inicioUsuarioController;
    }
}
