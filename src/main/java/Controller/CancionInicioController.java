package Controller;

import App.MainApp;
import Model.Cancion;
import Model.InicioSesion;
import Model.Tienda;
import com.sun.source.tree.InstanceOfTree;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

public class CancionInicioController {
    @FXML
    private Label anioCancion;

    @FXML
    private ImageView caratulaCancion;

    @FXML
    private SVGPath interaccionCancion;

    @FXML
    private Label duracionCancion;

    @FXML
    private Label generoCancion;

    @FXML
    private Label nombreAlbum;

    @FXML
    private Label nombreCancion;
    private PopUpDeshacerController deshacerController= new PopUpDeshacerController();
    private PopUpRehacerController rehacerController;
    private Tienda tienda= Tienda.getInstance();
    private final InicioSesion inicioSesion= InicioSesion.getInstance();

    private InicioUsuarioController inicioUsuarioController;
    private boolean estaEnPlayList;
    private Cancion cancion=null;

    public void cargarDatos(Cancion cancion1){
        cancion =cancion1;
        nombreCancion.setText( cancion.getNombreCancion() );
        generoCancion.setText( cancion.getGenero() );
        duracionCancion.setText( ""+cancion.getDuracion() );
        nombreAlbum.setText(cancion.getNombreAlbum());
        anioCancion.setText(""+cancion.getAnio());
    }
    public void setInteraccionCancion(boolean interaccion){
        this.estaEnPlayList= interaccion;
    }
    public void setIconoInteraccion(){
        if(!estaEnPlayList){
            interaccionCancion.setContent("M33,7.64c-1.34-2.75-5.2-5-9.69-3.69A9.87,9.87,0,0,0,18,7.72a9.87,9.87,0,0,0-5.31-3.77C8.19,2.66,4.34,4.89,3,7.64c-1.88,3.85-1.1,8.18,2.32,12.87C8,24.18,11.83,27.9,17.39,32.22a1,1,0,0,0,1.23,0c5.55-4.31,9.39-8,12.07-11.71C34.1,15.82,34.88,11.49,33,7.64Z");
        }
        else{
            interaccionCancion.setContent("M10.185,1.417c-4.741,0-8.583,3.842-8.583,8.583c0,4.74,3.842,8.582,8.583,8.582S18.768,14.74,18.768,10C18.768,5.259,14.926,1.417,10.185,1.417 M10.185,17.68c-4.235,0-7.679-3.445-7.679-7.68c0-4.235,3.444-7.679,7.679-7.679S17.864,5.765,17.864,10C17.864,14.234,14.42,17.68,10.185,17.68 M10.824,10l2.842-2.844c0.178-0.176,0.178-0.46,0-0.637c-0.177-0.178-0.461-0.178-0.637,0l-2.844,2.841L7.341,6.52c-0.176-0.178-0.46-0.178-0.637,0c-0.178,0.176-0.178,0.461,0,0.637L9.546,10l-2.841,2.844c-0.178,0.176-0.178,0.461,0,0.637c0.178,0.178,0.459,0.178,0.637,0l2.844-2.841l2.844,2.841c0.178,0.178,0.459,0.178,0.637,0c0.178-0.176,0.178-0.461,0-0.637L10.824,10z");
            interaccionCancion.setScaleX(2);
            interaccionCancion.setScaleY(2);
        }
    }
    public void reproducirCancion(){
        inicioUsuarioController.reproducirCancion(cancion);
    }
    public void setInicioUsuarioController(InicioUsuarioController inicioUsuarioController) {
        this.inicioUsuarioController = inicioUsuarioController;
    }


    public void agregarCancionPlaylist() throws IOException {

        System.out.println(cancion.getNombreCancion());

        if(!estaEnPlayList) {

            tienda.agregarCancion(inicioSesion.getUsuario(), cancion);

            System.out.println("Se agrega la cancion: " + cancion.getNombreCancion());

            System.out.println();
            System.out.println("Canciones actualizadas:");

            int contador = 0;

            for (Cancion cancion : inicioSesion.getUsuario().getCancionesFav()) {

                if(contador == inicioSesion.getUsuario().getCancionesFav().getTamanio()){
                    break;
                } else {
                    System.out.println(cancion.getNombreCancion());
                }
                contador++;
            }

            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/View/PopUpDeshacer.fxml"));
            Parent parent = loader.load();
            deshacerController= loader.getController();
            deshacerController.setInicioUsuarioController(inicioUsuarioController);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            scene.setFill(Color.TRANSPARENT);
            stage.show();

        } else {

            tienda.eliminarCancion(inicioSesion.getUsuario(), cancion);
            inicioUsuarioController.pintarPlaylist();

            System.out.println();
            System.out.println("Canciones actualizadas:");

            int contador = 0;

            for (Cancion cancion : inicioSesion.getUsuario().getCancionesFav()) {

                if(contador == inicioSesion.getUsuario().getCancionesFav().getTamanio()){
                    break;
                } else {
                    System.out.println(cancion.getNombreCancion());
                }
                contador++;
            }

            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/View/PopUpDeshacer.fxml"));
            Parent parent = loader.load();
            deshacerController = loader.getController();
            deshacerController.setInicioUsuarioController(inicioUsuarioController);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            scene.setFill(Color.TRANSPARENT);
            stage.show();

        }
    }
}
