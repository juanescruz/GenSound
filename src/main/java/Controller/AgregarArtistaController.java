package Controller;

import Model.Artista;
import Model.Tienda;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    public void agregarArtista(){
        tienda.agregarArtista(new Artista(Integer.parseInt(codigoArtista.getText()),nombreArtista.getText(),
                nacionalidad.getText(),esGrupo.isPressed(),null));
        System.out.println(tienda.getArtistas());
    }

    public String generoMasPopular(){
        return "";
    }
}