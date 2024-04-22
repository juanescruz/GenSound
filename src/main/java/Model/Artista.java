package Model;

import Estructuras.Lista.ListaDoble;
import lombok.*;

@Data
@AllArgsConstructor
public class Artista {

    private int codigoArtista;
    private String nombreArtista;
    private String nacionalidad;
    private boolean esGrupo;
    private ListaDoble canciones;

    public int getCodigoArtista() {
        return codigoArtista;
    }

    public void setCodigoArtista(int codigoArtista) {
        this.codigoArtista = codigoArtista;
    }

    public String getNombreArtista() {
        return nombreArtista;
    }

    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista = nombreArtista;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public boolean isEsGrupo() {
        return esGrupo;
    }

    public void setEsGrupo(boolean esGrupo) {
        this.esGrupo = esGrupo;
    }

    public ListaDoble getCanciones() {
        return canciones;
    }

    public void setCanciones(ListaDoble canciones) {
        this.canciones = canciones;
    }

}
