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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

import java.net.URL;
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
            for (int i = 0; i < tienda.getArtistas().getTamanio(); i++) {
                listaCanciones.getChildren().add(cargarCancion( new Cancion("2", "Cancion prueba", "", 2021, 20, "", "") ));
                listaArtistas.getChildren().add(cargarArtistas(new Artista(1,"a","a",false, new ListaDoble<>())));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public Parent cargarCancion(Cancion cancion) throws Exception{

        FXMLLoader loader = new FXMLLoader( MainApp.class.getResource("/view/cancion.fxml") );
        Parent parent = loader.load();

        CancionController controller = loader.getController();
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
