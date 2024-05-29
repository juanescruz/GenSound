package Controller;

import Model.Cancion;
import Model.Tienda;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.random.RandomGenerator;

public class AgregarCancionController implements Initializable {

    @FXML
    private TextField codArtista;
    @FXML
    private TextField anioCancion;

    @FXML
    private Button btnMostrar;
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

    @FXML
    private ImageView previewCaratula;


    private Tienda tienda = Tienda.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        generoCancion.getItems().addAll(
                "Rock", "Pop", "Punk", "Reggaeton","Electrónica"
        );

    }
    /**
     * Método público para agregar una canción a un artista en específico.
     */
    public void agregarCancion(){
        try {
            tienda.agregarCancion(new Cancion(RandomGenerator.getDefault().nextInt(),nombreCancion.getText(),
                    nombreAlbum.getText(),Integer.parseInt(anioCancion.getText()),
                    Double.parseDouble(duracionCancion.getText()), generoCancion.getValue(),
                    urlCancion.getText(), direccionCaratula.getText()), Integer.parseInt(codArtista.getText()));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cuidado");
            alert.setContentText("Ingrese un artista existente");
            alert.show();
            throw new RuntimeException(e);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setContentText("Canción agregada correctamente");
        alert.show();
    }
    /**
     * Método público para mostrar una imagen en el componente `previewCaratula`.
     */
    public void mostrarImagen(){
        String imagePath = direccionCaratula.getText();
        Image image = null;
        try {
            image = new Image(getClass().getClassLoader().getResourceAsStream(imagePath));
            previewCaratula.setImage(image);
        } catch (NullPointerException e) {
            System.err.println("No se pudo cargar la imagen en la ruta: " + imagePath);
            e.printStackTrace();
        }
    }
}
