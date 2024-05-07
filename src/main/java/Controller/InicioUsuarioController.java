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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
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
    private VBox vBoxCanciones;

    private Tienda tienda= Tienda.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Esto solo se va a hacer para mostrar que necesitamos una lista de canciones, pero la
        //playlist del usuario se debe sacar de otra manera
        ArrayList<Cancion> canciones= new ArrayList<>();
        try {
            for (int i = 0; i < canciones.size(); i++) {
                vBoxCanciones.getChildren().add(cargarCancionInicio(canciones.get(i)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public Parent cargarCancionPlayList(Cancion cancion) throws Exception{

        FXMLLoader loader = new FXMLLoader( MainApp.class.getResource("/View/CancionPlaylist.fxml") );
        Parent parent = loader.load();

        CancionInicioController controller = loader.getController();
        controller.cargarDatos(cancion);

        return parent;

    }
    public Parent cargarCancionInicio(Cancion cancion) throws Exception{

        FXMLLoader loader = new FXMLLoader( MainApp.class.getResource("/View/CancionInicioUsuario.fxml") );
        Parent parent = loader.load();

        CancionInicioController controller = loader.getController();
        controller.cargarDatos(cancion);

        return parent;

    }

    public void cargarCancionesInicio(){
        //Esto solo se va a hacer para mostrar que necesitamos una lista de canciones, pero la
        //playlist del usuario se debe sacar de otra manera
        ArrayList<Cancion> canciones= new ArrayList<>();
        try {
            for (int i = 0; i < canciones.size(); i++) {
                vBoxCanciones.getChildren().add(cargarCancionInicio(canciones.get(i)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void cargarPlaylist(){
        ArrayList<Cancion> playlist= new ArrayList<>();
        try {
            for (int i = 0; i < playlist.size(); i++) {
                vBoxCanciones.getChildren().add(cargarCancionPlayList(playlist.get(i)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
