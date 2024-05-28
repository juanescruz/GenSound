package Controller;

import App.MainApp;
import Model.Cancion;
import Model.InicioSesion;
import Model.Tienda;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class InicioUsuarioController implements Initializable {
    @FXML
    public BorderPane BorderPane;
    @FXML
    private TextField txtBuscar;
    @FXML
    private SVGPath volver;
    @FXML
    private RadioButton radioButtonArtista;

    @FXML
    private RadioButton radioButtonO;

    @FXML
    private RadioButton radioButtonY;
    @FXML
    public VBox vBoxCanciones;
    @FXML
    private VBox vboxLista;
    @FXML
    private ComboBox<String> comboBoxAtributos;

    @FXML
    private Button btnOrdenarAscendente;

    @FXML
    private Button btnOrdenarDescendente;
    private ReproductorController reproductorController;
    private CancionInicioController cancionInicioController;
    private final InicioSesion inicioSesion= InicioSesion.getInstance();
    private Stage currentPopup;
    private Tienda tienda= Tienda.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        txtBuscar.setPromptText("Ingresa Albúm,Nombre de la canción para busqueda Y/O");
        cargarComboBoxAtributos();
        pintarCancionesInicio();
        FXMLLoader loader = new FXMLLoader( MainApp.class.getResource("/View/Reproductor.fxml") );
        try {
            Parent parent = loader.load();
            reproductorController = loader.getController();
            reproductorController.setInicioUsuarioController(this);
            vboxLista.getChildren().add(1, parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        btnOrdenarAscendente.setOnAction(event -> ordenarCanciones(true));
        btnOrdenarDescendente.setOnAction(event -> ordenarCanciones(false));

    }
    private void cargarComboBoxAtributos() {
        comboBoxAtributos.getItems().addAll("Nombre", "Album", "Año", "Duración", "Género");
        comboBoxAtributos.setOnAction(event -> ordenarCanciones(true));
    }

    public void pintarCancionesInicio() {

        vBoxCanciones.getChildren().clear();
        try {
            List<Cancion> canciones= tienda.obtenerCanciones();

            for (Cancion cancion : canciones) {



                vBoxCanciones.getChildren().add(cargarCancionInicio(cancion));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public Parent cargarCancionInicio(Cancion cancion) throws Exception{

        FXMLLoader loader = new FXMLLoader( MainApp.class.getResource("/View/CancionInicio.fxml") );
        Parent parent = loader.load();

        cancionInicioController = loader.getController();
        cancionInicioController.setInicioUsuarioController(this);
        cancionInicioController.setInteraccionCancion(false);
        cancionInicioController.setIconoInteraccion();
        cancionInicioController.cargarDatos(cancion);

        return parent;

    }

    public void showPopup(Stage popup) {
        if (currentPopup != null && currentPopup.isShowing()) {
            currentPopup.close();
        }
        currentPopup = popup;
        currentPopup.show();
    }

    public void reproducirCancion(Cancion cancion) {
        reproductorController.setURLCancion(cancion.getUrl());
    }

    @FXML
    void buscar(ActionEvent event) throws Exception {
        Set<Cancion> cancionesSet = new HashSet<>();
        String parametros = txtBuscar.getText();
        String[] atributos = parametros.split(",");

        if (radioButtonArtista.isSelected()) {
            if(atributos.length>1){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setContentText("Ingrese solo el nombre del artista.");
                alert.show();
            }else{
                System.out.println("Parametro: " + parametros);
                cancionesSet.addAll(tienda.buscarArtista(atributos[0]));
            }
        } else if (radioButtonO.isSelected()) {
            if (atributos.length < 2) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setContentText("Ingrese al menos dos parámetros separados por comas para hacer la búsqueda O.");
                alert.show();
                throw new Exception("Ingrese al menos dos parámetros separados por comas para hacer la búsqueda O.");
            }
            cancionesSet.addAll(tienda.buscarCancionesO(atributos));
        } else if (radioButtonY.isSelected()) {
            if (atributos.length < 2) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setContentText("Ingrese al menos dos parámetros separados por comas para hacer la búsqueda Y.");
                alert.show();
                throw new Exception("Ingrese al menos dos parámetros separados por comas para hacer la búsqueda Y.");
            }
            cancionesSet.addAll(tienda.buscarCancionesY(atributos));
        }

        List<Cancion> canciones = new ArrayList<>(cancionesSet);
        System.out.println("Canciones en controller: " + canciones);
        Label label = new Label();
        label.setText("No se encontró ninguna coincidencia");

        if (canciones.isEmpty()) {
            vBoxCanciones.getChildren().clear();
            vBoxCanciones.getChildren().add(label);
        } else {
            vBoxCanciones.getChildren().clear();
            for (Cancion cancion : canciones) {
                try {
                    vBoxCanciones.getChildren().add(cargarCancionInicio(cancion));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    public Parent cargarCancionPlayList(Cancion cancion) throws Exception{

        FXMLLoader loader = new FXMLLoader( MainApp.class.getResource("/View/CancionInicio.fxml") );
        Parent parent = loader.load();

        cancionInicioController = loader.getController();
        cancionInicioController.cargarDatos(cancion);
        cancionInicioController = loader.getController();
        cancionInicioController.setInicioUsuarioController(this);
        cancionInicioController.setInteraccionCancion(true);
        cancionInicioController.setIconoInteraccion();
        cancionInicioController.cargarDatos(cancion);

        return parent;

    }

    private static void mostrarInformacionDeLaLista() {

        System.out.println();
        System.out.println("Canciones actuales en la playlist: ");

        int contador = 0;

        for (Cancion cancion : InicioSesion.getInstance().getUsuario().getCancionesFav()) {

            if(contador == InicioSesion.getInstance().getUsuario().getCancionesFav().getTamanio()){
                break;
            } else {
                System.out.println(cancion.getNombreCancion());
            }
            contador++;
        }
    }

    public void onPlayListClick(){
        mostrarInformacionDeLaLista();
        pintarPlaylist();
    }

    public void pintarPlaylist(){

        vBoxCanciones.getChildren().clear();

        try {

            int contador = 0;

            for (Cancion cancion : inicioSesion.getUsuario().getCancionesFav()) {

                if(contador == inicioSesion.getUsuario().getCancionesFav().getTamanio()){
                    break;

                } else {
                    vBoxCanciones.getChildren().add(cargarCancionPlayList(cancion));
                }
                contador++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void ordenarCanciones(boolean ascendente) {
        List<Cancion> canciones = tienda.obtenerCanciones();
        if (canciones != null && !canciones.isEmpty()) {
            String atributo = comboBoxAtributos.getValue();
            if (atributo != null && !atributo.isEmpty()) {
                Comparator<Cancion> comparator;
                switch (atributo) {
                    case "Nombre":
                        comparator = Comparator.comparing(Cancion::getNombreCancion);
                        break;
                    case "Album":
                        comparator = Comparator.comparing(Cancion::getNombreAlbum);
                        break;
                    case "Año":
                        comparator = Comparator.comparingInt(Cancion::getAnio);
                        break;
                    case "Duración":
                        comparator = Comparator.comparingDouble(Cancion::getDuracion);
                        break;
                    case "Género":
                        comparator = Comparator.comparing(Cancion::getGenero);
                        break;
                    default:
                        return;
                }
                if (!ascendente) {
                    comparator = comparator.reversed();
                }
                canciones.sort(comparator);
                actualizarListaCanciones(canciones);
            }
        }
    }

    private void actualizarListaCanciones(List<Cancion> canciones) {
        vBoxCanciones.getChildren().clear();
        for (Cancion cancion : canciones) {
            try {
                vBoxCanciones.getChildren().add(cargarCancionInicio(cancion));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void volverInicioSesion() {
        try{
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/View/inicioSesion.fxml"));
        Parent parent = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("GenSound");
        Stage scene1 = (Stage) txtBuscar.getParent().getScene().getWindow();
        scene1.close();
        stage.show();
    } catch (IOException ioException) {
        ioException.printStackTrace();
    }
    }

}


