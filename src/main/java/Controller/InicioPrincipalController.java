package Controller;

import App.MainApp;
import Model.Cancion;
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
    private GridPane gridArtistas;

    @FXML
    private VBox gridCanciones;

    @FXML
    private SVGPath logo;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        //trarse las canciones del artista X y por cada cancion hacer lo de abajo

        try {
            for (int i = 0; i < 3; i++) {
                gridCanciones.getChildren().add(cargarCancion( new Cancion(1, "Cancion prueba", "", 2021, 20, "", "") ));
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

}
