package Controller;

import App.MainApp;
import Model.Artista;
import Model.Cancion;
import Model.InicioSesion;
import Model.Tienda;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.*;

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
    private VBox vBoxCanciones;

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
    private final InicioSesion inicioSesion = InicioSesion.getInstance();
    private final Tienda tienda = Tienda.getInstance();

    // Lista para almacenar las canciones buscadas temporalmente
    private List<Cancion> cancionesBuscadas = new ArrayList<>();

    // Lista para almacenar las canciones de la playlist temporalmente
    private List<Cancion> cancionesPlaylist = new ArrayList<>();

    private boolean buscandoEnPlaylist = false; // Para saber si se está buscando en la playlist

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup toggleGroup = new ToggleGroup();
        radioButtonArtista.setToggleGroup(toggleGroup);
        radioButtonO.setToggleGroup(toggleGroup);
        radioButtonY.setToggleGroup(toggleGroup);

        txtBuscar.setPromptText("Ingresa Albúm,Nombre de la canción para busqueda Y/O");
        cargarComboBoxAtributos();
        pintarCancionesInicio();
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/View/Reproductor.fxml"));
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
        buscandoEnPlaylist = false;
        try {
            List<Cancion> canciones = tienda.obtenerCanciones();
            for (int i = 0; i < canciones.size(); i++) {
                vBoxCanciones.getChildren().add(cargarCancionInicio(canciones.get(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Parent cargarCancionInicio(Cancion cancion) throws Exception {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/View/CancionInicio.fxml"));
        Parent parent = loader.load();

        cancionInicioController = loader.getController();
        cancionInicioController.setInicioUsuarioController(this);
        cancionInicioController.setInteraccionCancion(false);
        cancionInicioController.setIconoInteraccion();
        cancionInicioController.cargarDatos(cancion);
        return parent;
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
            if (atributos.length > 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setContentText("Ingrese solo el nombre del artista.");
                alert.show();
            } else {
                System.out.println("Parametro: " + parametros);
                System.out.println("Está buscando en playlist: "+buscandoEnPlaylist);
                if (buscandoEnPlaylist) {
                    cancionesSet.addAll(buscarArtistaEnPlaylist(atributos[0]));
                } else {
                    cancionesSet.addAll(tienda.buscarArtista(atributos[0]));
                }
            }
        } else if (radioButtonO.isSelected()) {
            if (atributos.length < 2) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setContentText("Ingrese al menos dos parámetros separados por comas para hacer la búsqueda O.");
                alert.show();
                throw new Exception("Ingrese al menos dos parámetros separados por comas para hacer la búsqueda O.");
            }
            if (buscandoEnPlaylist) {
                cancionesSet.addAll(buscarCancionesOEnPlaylist(atributos));
            } else {
                cancionesSet.addAll(tienda.buscarCancionesO(atributos));
            }
        } else if (radioButtonY.isSelected()) {
            if (atributos.length < 2) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setContentText("Ingrese al menos dos parámetros separados por comas para hacer la búsqueda Y.");
                alert.show();
                throw new Exception("Ingrese al menos dos parámetros separados por comas para hacer la búsqueda Y.");
            }
            if (buscandoEnPlaylist) {
                cancionesSet.addAll(buscarCancionesYEnPlaylist(atributos));
            } else {
                cancionesSet.addAll(tienda.buscarCancionesY(atributos));
            }
        }

        cancionesBuscadas = new ArrayList<>(cancionesSet); // Almacenar las canciones buscadas
        System.out.println("Canciones en controller: " + cancionesBuscadas);
        Label label = new Label();
        label.setText("No se encontró ninguna coincidencia");

        if (cancionesBuscadas.isEmpty()) {
            vBoxCanciones.getChildren().clear();
            vBoxCanciones.getChildren().add(label);
        } else {
            actualizarListaCanciones(cancionesBuscadas);
        }
    }

    public Parent cargarCancionPlayList(Cancion cancion) throws Exception {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/View/CancionInicio.fxml"));
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

    public void pintarPlaylist() {
        vBoxCanciones.getChildren().clear();
        cancionesPlaylist.clear(); // Limpiar la lista de canciones de la playlist
        try {
            int contador = 0;
            for (Cancion cancion : inicioSesion.getUsuario().getCancionesFav()) {
                if (contador == inicioSesion.getUsuario().getCancionesFav().getTamanio()) {
                    break;
                } else {
                    vBoxCanciones.getChildren().add(cargarCancionPlayList(cancion));
                    cancionesPlaylist.add(cancion); // Añadir la canción a la lista de canciones de la playlist
                }
                contador++;
            }
            // Actualizar el estado de buscandoEnPlaylist
            buscandoEnPlaylist = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void ordenarCanciones(boolean ascendente) {
        List<Cancion> cancionesAOrdenar;

        if (!cancionesBuscadas.isEmpty()) {
            cancionesAOrdenar = new ArrayList<>(cancionesBuscadas);
        } else if (buscandoEnPlaylist && !cancionesPlaylist.isEmpty()) {
            cancionesAOrdenar = new ArrayList<>(cancionesPlaylist);
        } else {
            cancionesAOrdenar = tienda.obtenerCanciones();
        }

        if (cancionesAOrdenar != null && !cancionesAOrdenar.isEmpty()) {
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
                cancionesAOrdenar.sort(comparator);
                actualizarListaCanciones(cancionesAOrdenar);
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

    private List<Cancion> buscarArtistaEnPlaylist(String artistaBuscado) {
        List<Cancion> resultado = new ArrayList<>();
        List<Artista> artistas = Tienda.getInstance().obtenerTodosLosArtistas();
        for (Artista artista : artistas) {
            if (artista.getNombreArtista().equalsIgnoreCase(artistaBuscado)) {
                for (Cancion cancionDelArtista : artista.getCanciones()) {
                    for (Cancion cancionPlaylist : cancionesPlaylist) {
                        if (cancionDelArtista.equals(cancionPlaylist)) {
                            resultado.add(cancionDelArtista);
                            break;
                        }
                    }
                }
                break;
            }
        }
        return resultado;
    }



    private List<Cancion> buscarCancionesOEnPlaylist(String[] atributos) {
        List<Cancion> resultado = new ArrayList<>();
        for (Cancion cancion : cancionesPlaylist) {
            for (String atributo : atributos) {
                if (cancion.getNombreCancion().equalsIgnoreCase(atributo) ||
                        cancion.getNombreAlbum().equalsIgnoreCase(atributo) ||
                        cancion.getGenero().equalsIgnoreCase(atributo) ||
                        String.valueOf(cancion.getAnio()).equals(atributo) ||
                        String.valueOf(cancion.getDuracion()).equals(atributo)) {
                    resultado.add(cancion);
                    break;
                }
            }
        }
        return resultado;
    }

    private List<Cancion> buscarCancionesYEnPlaylist(String[] atributos) {
        List<Cancion> resultado = new ArrayList<>();
        for (Cancion cancion : cancionesPlaylist) {
            boolean coincide = true;
            for (String atributo : atributos) {
                if (!(cancion.getNombreCancion().equalsIgnoreCase(atributo) ||
                        cancion.getNombreAlbum().equalsIgnoreCase(atributo) ||
                        cancion.getGenero().equalsIgnoreCase(atributo) ||
                        String.valueOf(cancion.getAnio()).equals(atributo) ||
                        String.valueOf(cancion.getDuracion()).equals(atributo))) {
                    coincide = false;
                    break;
                }
            }
            if (coincide) {
                resultado.add(cancion);
            }
        }
        return resultado;
    }
}
