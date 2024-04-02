package Model;

import Estructuras.Lista.ListaDoble;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Artista {
    private int codigoArtista;
    private String nombreArtista;
    private String nacionalidad;
    private boolean esGrupo;
    private ListaDoble canciones;
}
