package Model;

import Estructuras.Lista.ListaDoble;
import lombok.*;

@Data

public class Artista {

    @Getter
    private int codigoArtista;
    @Getter
    private String nombreArtista;
    @Getter
    private String nacionalidad;
    @Getter
    private boolean esGrupo;
    @Getter
    private ListaDoble<Cancion> canciones;

    public Artista(int codigoArtista, String nombreArtista, String nacionalidad, boolean esGrupo, ListaDoble<Cancion> canciones) {
        this.codigoArtista = codigoArtista;
        this.nombreArtista = nombreArtista;
        this.nacionalidad = nacionalidad;
        this.esGrupo = esGrupo;
        this.canciones = canciones != null ? canciones : new ListaDoble<>();

    }

    public void setCodigoArtista(int codigoArtista) {
        this.codigoArtista = codigoArtista;
    }

    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista = nombreArtista;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public void setEsGrupo(boolean esGrupo) {
        this.esGrupo = esGrupo;
    }

    public void setCanciones(ListaDoble<Cancion> canciones) {
        this.canciones = canciones;
    }

}
