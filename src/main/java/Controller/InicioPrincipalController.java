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
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class InicioPrincipalController implements Initializable {

    @FXML
    private VBox listaArtistas;

    @FXML
    private VBox listaCanciones;

    @FXML
    private SVGPath logo;

    private Tienda tienda = Tienda.getInstance();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
           ArrayList<Artista> artistas= tienda.obtenerArtistas();
            for (int i = 0; i < artistas.size(); i++) {
                listaArtistas.getChildren().add(cargarArtistas(artistas.get(i)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            ArrayList<Cancion> canciones= tienda.obtenerCanciones();
            for (int i = 0; i<canciones.size(); i++) {
                listaCanciones.getChildren().add(cargarCancion(canciones.get(i)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public Parent cargarCancion(Cancion cancion) throws Exception{

        FXMLLoader loader = new FXMLLoader( MainApp.class.getResource("/View/CancionInicio.fxml") );
        Parent parent = loader.load();

        CancionInicioController controller = loader.getController();
        controller.cargarDatos(cancion);

        return parent;

    }

    public Parent cargarArtistas(Artista artista) throws Exception{

        FXMLLoader loader = new FXMLLoader( MainApp.class.getResource("/view/Artista.fxml") );
        Parent parent = loader.load();

        ArtistaController controller = loader.getController();
        controller.cargarDatos(artista);

        return parent;

    }

}
