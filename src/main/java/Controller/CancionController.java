package Controller;

import Model.Cancion;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.SVGPath;

public class CancionController {
    @FXML
    private Label anioCancion;

    @FXML
    private ImageView caratulaCancion;

    @FXML
    private Label duracionCancion;

    @FXML
    private Label generoCancion;

    @FXML
    private Label nombreAlbum;

    @FXML
    private Label nombreCancion;

    @FXML
    private SVGPath reproducirCancion;

    public void cargarDatos(Cancion cancion){
        nombreCancion.setText( cancion.getNombreCancion() );
        generoCancion.setText( cancion.getGenero() );
        duracionCancion.setText( ""+cancion.getDuracion() );
    }


}