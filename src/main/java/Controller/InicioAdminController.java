package Controller;

import Model.Tienda;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

import App.MainApp;

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
    public void abrirConsultas(){
        JOptionPane.showMessageDialog(null, "El género más escuchado de la tienda es: "+tienda.hallarGeneroMasRepetido());
    }
}
