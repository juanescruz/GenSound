package Controller;

import App.MainApp;
import Estructuras.Lista.ListaDoble;
import Model.Artista;
import Model.Cancion;
import Model.Tienda;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    private BorderPane listaCanciones;
    @FXML
    private VBox vBoxCanciones;
    @FXML
    private VBox vboxLista;

    private ReproductorPruebaController reproductorPruebaController;

    private CancionInicioController cancionInicioController;

    private Tienda tienda= Tienda.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            List<Cancion> canciones= tienda.obtenerCanciones();
            for (int i = 0; i<canciones.size(); i++) {
                vBoxCanciones.getChildren().add(cargarCancionInicio(canciones.get(i)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        FXMLLoader loader = new FXMLLoader( MainApp.class.getResource("/View/ReproductorPrueba.fxml") );

        try {
            Parent parent = loader.load();
            reproductorPruebaController = loader.getController();
            reproductorPruebaController.setInicioUsuarioController(this);
            vboxLista.getChildren().add(1, parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private void cambiarVentana(String fxmlname) {
        try {
            Node nodo = MainApp.loadFXML(fxmlname);
            setCenter(nodo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @SuppressWarnings("exports")
    public void setCenter(Node node) {
        listaCanciones.setCenter(node);
    }

    public void abrirPlaylist(){
        cambiarVentana("playlist");
        //Llamar metodo que inialice
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

        cancionInicioController = loader.getController();
        cancionInicioController.setInicioUsuarioController(this);
        cancionInicioController.cargarDatos(cancion);
        return parent;

    }


    public void reproducirCancion(Cancion cancion) {

        reproductorPruebaController.setURLCancion(cancion.getUrl());
    }
}
