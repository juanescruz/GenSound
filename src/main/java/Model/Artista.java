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



}
