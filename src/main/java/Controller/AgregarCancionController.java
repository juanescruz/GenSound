package Controller;

import Model.Cancion;
import Model.Tienda;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AgregarCancionController implements Initializable {

    @FXML
    private TextField nomArtista;
    @FXML
    private TextField anioCancion;

    @FXML
    private Button btnAgregar;

    @FXML
    private TextField direccionCaratula;

    @FXML
    private TextField duracionCancion;

    @FXML
    private ComboBox<String> generoCancion;

    @FXML
    private TextField nombreAlbum;

    @FXML
    private TextField nombreCancion;

    @FXML
    private TextField urlCancion;


    private Tienda tienda = Tienda.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generoCancion.getItems().addAll(
                "Rock", "Pop", "Punk", "Reggaeton","Electrónica"
        );
    }

    public void agregarCancion(){
        tienda.agregarCancion(new Cancion(1,nombreCancion.getText(),
                nombreAlbum.getText(),Integer.parseInt(anioCancion.getText()),
                Double.parseDouble(duracionCancion.getText()), generoCancion.getItems().toString(),
                urlCancion.toString()), Integer.parseInt(nomArtista.getText()));
    }
}
