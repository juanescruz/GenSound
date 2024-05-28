package Controller;

import Model.Tienda;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

import App.MainApp;
import javafx.stage.Stage;

import javax.swing.*;

public class InicioAdminController {

    @FXML
    private HBox consultas;
    @FXML
    private HBox agregarArtista;
    @FXML
    private HBox crearCancion;
    @FXML
    private BorderPane centerPane;

    private Tienda tienda= Tienda.getInstance();

    /**
     * Método para camciar ventanas.
     */
    private void cambiarVentana(String fxmlname) {
        try {
            Node nodo = MainApp.loadFXML(fxmlname);
            setCenter(nodo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @SuppressWarnings("exports")
    public void setCenter(Node node) {
        centerPane.setCenter(node);
    }


    public void abrirAgregarAr(){ cambiarVentana("AgregarAr");}
    public void abrirAgregarCan(){ cambiarVentana("AgregarCan");}

    /**
     * Método para abrir la ventana de consultas de estadísticas.
     */
    public void abrirConsultas() {
        try{
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/View/Estadisticas.fxml"));
            Parent parent = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.setTitle("GenSound");
            stage.show();
        } catch (IOException ioException ) {
            ioException.printStackTrace();
        } catch(NullPointerException e){
            JOptionPane.showMessageDialog(null,"No hay canciones likeadas");
        }
    }
    /**
     * Método para abrir la ventana de inicioSesion.
     */
    public void volverInicioSesion() {
        try{
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/View/inicioSesion.fxml"));
            Parent parent = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.setTitle("GenSound");
            Stage scene1 = (Stage) consultas.getParent().getScene().getWindow();
            scene1.close();
            stage.show();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
