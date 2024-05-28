package Controller;

import Estructuras.Lista.ListaDoble;
import Model.Artista;
import Model.Tienda;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;


import java.net.URL;
import java.util.ResourceBundle;

public class AgregarArtistaController implements Initializable {
    @FXML
    private Button btnAgregar;
    @FXML
    private TextField codigoArtista;

    @FXML
    private RadioButton esGrupo;

    @FXML
    private TextField nacionalidad;

    @FXML
    private TextField nombreArtista;

    private Tienda tienda = Tienda.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Método público para agregar un nuevo artista a la tienda.
     * @throws Exception Si ocurre algún error durante la adición del artista a la tienda.
     */
    public void agregarArtista() throws Exception {
        tienda.agregarArtista(new Artista(Integer.parseInt(codigoArtista.getText()),nombreArtista.getText(),
                nacionalidad.getText(),esGrupo.isPressed(),new ListaDoble<>()));
        System.out.println(tienda.getArtistas());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setContentText("Artista agregada correctamente");
        alert.show();
    }

}