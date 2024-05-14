package Controller;

import App.MainApp;
import Estructuras.Lista.ListaDoble;
import Model.Artista;
import Model.Cancion;
import Model.Tienda;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class InicioUsuarioController implements Initializable {


    @FXML
    private Button btnBuscar;

    @FXML
    private TextField txtBuscar;

    @FXML
    private RadioButton radioButtonArtista;

    @FXML
    private RadioButton radioButtonO;

    @FXML
    private RadioButton radioButtonY;

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
        txtBuscar.setPromptText("Ingresa Albúm,Nombre de la canción para busqueda Y/O");
        pintarCancionesInicio();
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

    public void pintarCancionesInicio() {
        vBoxCanciones.getChildren().clear();
        try {
            List<Cancion> canciones= tienda.obtenerCanciones();
            for (int i = 0; i<canciones.size(); i++) {
                vBoxCanciones.getChildren().add(cargarCancionInicio(canciones.get(i)));
            }
        }catch (Exception e){
            e.printStackTrace();
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

    @FXML
    void buscar(ActionEvent event) throws Exception {
        List<Cancion>canciones = new ArrayList<>();
        String parametros = txtBuscar.getText();
        if (radioButtonArtista.isSelected()) {
            System.out.println("Parametro: "+parametros);
            canciones = tienda.buscarArtista(parametros);
        } else if (radioButtonO.isSelected()) {
            String[] atributos = parametros.split(",");
            if(atributos.length == 2){
                canciones = tienda.buscarCancionesO(atributos[0], atributos[1]);
            }else{
                throw new Exception("Ingrese los dos parametros separados por , para hacer la busqueda Or");
            }
        } else if (radioButtonY.isSelected()) {
            String[] atributos = parametros.split(",");
            if(atributos.length == 2){
                canciones = tienda.buscarCancionesY(atributos[0], atributos[1]);
            }else{
                throw new Exception("Ingrese los dos parametros separados por , para hacer la busqueda Y");
            }
        }
        System.out.println("Canciones en controller: "+canciones);
        Label label = new Label();
        label.setText("No se encontró ninguna coincidencia");

        if(canciones.isEmpty() || canciones == null){
            vBoxCanciones.getChildren().clear();
            vBoxCanciones.getChildren().add(label);
        }else{
            vBoxCanciones.getChildren().clear();
            for (int i = 0; i<canciones.size(); i++) {
                try {
                    vBoxCanciones.getChildren().add(cargarCancionInicio(canciones.get(i)));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
