package Controller;

import Model.Artista;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.awt.*;

public class ArtistaController {

    @FXML
    private Label nombreArtista;


    public void cargarDatos(Artista artista) {
        nombreArtista.setText(artista.getNombreArtista());
    }
}
