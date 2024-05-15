package Controller;

import Model.Cancion;
import Model.InicioSesion;
import Model.Tienda;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.SVGPath;

import javax.swing.*;

public class CancionPlaylistController {

    @FXML
    private Label anioCancion;

    @FXML
    private ImageView caratulaCancion;

    @FXML
    private Label duracionCancion;

    @FXML
    private SVGPath eliminarCancion;

    @FXML
    private Label generoCancion;

    @FXML
    private Label nombreAlbum;

    @FXML
    private Label nombreCancion;

    @FXML
    private SVGPath reproducirCancion;

    private Cancion cancion;

    private Tienda tienda= Tienda.getInstance();
    private final InicioSesion inicioSesion= InicioSesion.getInstance();

    public void cargarDatos(Cancion cancion){
        this.cancion=cancion;
        nombreCancion.setText( cancion.getNombreCancion() );
        generoCancion.setText( cancion.getGenero() );
        duracionCancion.setText( ""+cancion.getDuracion() );
        nombreAlbum.setText(cancion.getNombreAlbum());
        anioCancion.setText(""+cancion.getAnio());
    }

    public void eliminar(){
        tienda.eliminarCancion(inicioSesion.getUsuario(), cancion);
        int deshacer= JOptionPane.showConfirmDialog(null, "Desea deshacer lo hecho?");
        if(deshacer==1){
            inicioSesion.getUsuario().getCancionesFav().deshacer();
        }
    }

}
