package Controller;

import Model.Cancion;
import Model.InicioSesion;
import Model.Tienda;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

public class CancionInicioController {
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

    private SVGPath gustarCancion;

    private Tienda tienda= Tienda.getInstance();
    private final InicioSesion inicioSesion= InicioSesion.getInstance();

    private InicioUsuarioController inicioUsuarioController;

    private Cancion cancion=null;

    public void cargarDatos(Cancion cancion){
        this.cancion =cancion;
        nombreCancion.setText( cancion.getNombreCancion() );
        generoCancion.setText( cancion.getGenero() );
        duracionCancion.setText( ""+cancion.getDuracion() );
        nombreAlbum.setText(cancion.getNombreAlbum());
        anioCancion.setText(""+cancion.getAnio());
    }
    public void reproducirCancion(){
        VBox parent = (VBox) anioCancion.getParent();
        inicioUsuarioController.reproducirCancion(cancion);

    }
    public void setInicioUsuarioController(InicioUsuarioController inicioUsuarioController) {
        this.inicioUsuarioController = inicioUsuarioController;
    }

    public void agregarCancionPlaylist(){
        tienda.agregarCancion(inicioSesion.getUsuario(),cancion);
    }


}
