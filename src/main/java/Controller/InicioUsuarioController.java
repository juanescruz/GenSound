package Controller;

import App.MainApp;
import Estructuras.Lista.ListaDoble;
import Model.Artista;
import Model.Cancion;
import Model.Tienda;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class InicioUsuarioController implements Initializable {

    @FXML
    private RadioButton atriArtista;

    @FXML
    private RadioButton atriO;

    @FXML
    private RadioButton atriY;

    @FXML
    private TextField atributosBuscar;

    @FXML
    private ComboBox<String> atributosOrdenar;

    @FXML
    private Button btnInicio;

    @FXML
    private Button btnOrdenar;

    @FXML
    private Button btnPlaylist;

    @FXML
    private VBox listaCanciones;

    private Tienda tienda= Tienda.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ArrayList<Cancion> canciones= tienda.obtenerCanciones();
            for (int i = 0; i<canciones.size(); i++) {
                listaCanciones.getChildren().add(cargarCancionInicio(canciones.get(i)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Parent cargarCancionPlayList(Cancion cancion) throws Exception{

        FXMLLoader loader = new FXMLLoader( MainApp.class.getResource("/View/CancionPlaylist.fxml") );
        Parent parent = loader.load();

        CancionInicioController controller = loader.getController();
        controller.cargarDatos(cancion);

        return parent;

    }
    public Parent cargarCancionInicio(Cancion cancion) throws Exception{

        FXMLLoader loader = new FXMLLoader( MainApp.class.getResource("/View/CancionInicio.fxml") );
        Parent parent = loader.load();

        CancionInicioController controller = loader.getController();
        controller.cargarDatos(cancion);

        return parent;

    }




}
