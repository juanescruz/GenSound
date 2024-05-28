package Model;

import Estructuras.Lista.ListaDoble;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Artista implements Serializable{

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
