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
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class InicioUsuarioController implements Initializable {
    @FXML
    public BorderPane BorderPane;
    @FXML
    private TextField txtBuscar;

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

}


