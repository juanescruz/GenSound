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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import lombok.Getter;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class InicioUsuarioController implements Initializable {

    @FXML @Getter
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
    /**
     * Método para cargar los elementos en el ComboBox de atributos y configurar un evento de acción.
     */
    private void cargarComboBoxAtributos() {
        comboBoxAtributos.getItems().addAll("Nombre", "Album", "Año", "Duración", "Género");
        comboBoxAtributos.setOnAction(event -> ordenarCanciones(true));
    }
    /**
     * Método para pintar las canciones en la interfaz de inicio.
     */
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
    /**
     * Método para cargar y configurar un nodo de canción en la interfaz de inicio.
     * @param cancion La canción para la cual se cargará el nodo en la interfaz.
     * @return El nodo Parent que representa la canción cargada en la interfaz.
     * @throws Exception Si ocurre un error durante la carga del componente de canción de inicio.
     */
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
    /**
     * Método para mostrar un popup en la interfaz.
     * @param popup El Stage que representa el popup que se mostrará.
     */
    public void showPopup(Stage popup) {
        if (currentPopup != null && currentPopup.isShowing()) {
            currentPopup.close();
        }
        currentPopup = popup;
        currentPopup.show();
    }
    /**
     * Método para reproducir una canción en el reproductor.
     * @param cancion La canción que se reproducirá en el reproductor.
     */
    public void reproducirCancion(Cancion cancion) {
        reproductorController.setURLCancion(cancion.getUrl());
    }

    /**
     * Método para realizar una búsqueda de canciones según los criterios especificados por el usuario.
     * @param event El evento ActionEvent que desencadena la búsqueda.
     * @throws Exception Si ocurre algún error durante la búsqueda de canciones.
     */
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

    /**
     * Método para cargar y configurar un nodo de canción para mostrar en la lista de reproducción.
     * @param cancion La canción que se mostrará en el nodo de canción.
     * @return El nodo de canción cargado y configurado.
     * @throws Exception Si ocurre un error durante la carga del nodo de canción desde el archivo FXML.
     */
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
    /**
     * Método para mostrar la información de las canciones actuales en la lista de reproducción del usuario.
     * Imprime el nombre de cada canción en la lista de reproducción por consola.
     */
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
//        mostrarInformacionDeLaLista();
        pintarPlaylist();
    }
    /**
     * Método para representar gráficamente la lista de reproducción del usuario en la interfaz de usuario.
     */
    public void pintarPlaylist(){

        vBoxCanciones.getChildren().clear();

        try {

            System.out.println(  inicioSesion.getUsuario().getCancionesFav().getTamanio() );
            Iterator<Cancion> it = inicioSesion.getUsuario().getCancionesFav().iterator();

            while(it.hasNext()){
                Cancion cancion = it.next();
                System.out.println(cancion);
                vBoxCanciones.getChildren().add(cargarCancionPlayList(cancion));

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * Ordena las canciones según el atributo seleccionado de manera ascendente o descendente y actualiza la lista de canciones en la interfaz de usuario.
     * @param ascendente Un booleano que indica si las canciones deben ser ordenadas de manera ascendente (true) o descendente (false).
     */
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

    /**
     * Actualiza la lista de canciones en la interfaz de usuario con las canciones proporcionadas.
     * @param canciones La lista de canciones que se utilizará para actualizar la lista en la interfaz de usuario.
     */
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

    /**
     * Redirige a la ventana de inicio de sesión.
     */
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


