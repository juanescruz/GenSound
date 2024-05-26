package Controller;

import Model.Tienda;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;


public class EstadisticasController implements Initializable {

    @FXML
    private Label generoMas;
    @FXML
    private Label artistaMas;
    private Tienda tienda = Tienda.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generoMas.setText(tienda.hallarGeneroMasRepetido());
        artistaMas.setText(tienda.hallarArtistaMasPopular().getNombreArtista());
    }
}
