package Controller;

import App.MainApp;
import Estructuras.ListaCircular.IteradorCircular;
import Model.Cancion;
import Model.InicioSesion;
import Model.Tienda;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

public class PlaylistController implements Initializable {

    @FXML
    private VBox playlistUsuario;

    private Tienda tienda = Tienda.getInstance();
    private final InicioSesion inicioSesion= InicioSesion.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("a");
        
        try {
            for (Cancion cancion : inicioSesion.getUsuario().getCancionesFav()) {

                playlistUsuario.getChildren().add(cargarCancionPlayList(cancion));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public Parent cargarCancionPlayList(Cancion cancion) throws Exception{
        System.out.println(""+cancion.getNombreCancion());

        FXMLLoader loader = new FXMLLoader( MainApp.class.getResource("/View/CancionPlaylist.fxml") );
        Parent parent = loader.load();

        System.out.println(cancion.getNombreCancion());

        CancionPlaylistController controller = loader.getController();
        controller.cargarDatos(cancion);

        return parent;

    }
}
