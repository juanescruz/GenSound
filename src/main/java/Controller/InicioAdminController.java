package Controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

import App.MainApp;

public class InicioAdminController {

    @FXML
    private HBox consultas;
    @FXML
    private HBox agregarArtista;
    @FXML
    private HBox crearCancion;
    @FXML
    private BorderPane centerPane;

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
    public void abrirConsultas(){ cambiarVentana("Consultas");}
}
