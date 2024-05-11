package Controller;

import Model.Cancion;
import Model.Tienda;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.random.RandomGenerator;

public class AgregarCancionController implements Initializable {

    @FXML
    private TextField codArtista;
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
        try {
            tienda.agregarCancion(new Cancion(RandomGenerator.getDefault().nextInt(),nombreCancion.getText(),
                    nombreAlbum.getText(),Integer.parseInt(anioCancion.getText()),
                    Double.parseDouble(duracionCancion.getText()), generoCancion.getItems().toString(),
                    urlCancion.toString()), Integer.parseInt(codArtista.getText()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setContentText("Canción agregada correctamente");
        alert.show();
    }
}
