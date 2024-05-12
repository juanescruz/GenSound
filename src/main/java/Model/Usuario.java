package Model;

import Estructuras.ListaCircular.ListaCircular;
import lombok.*;

import java.io.Serializable;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario  implements Serializable {
    private String username;
    private String contrasenia;
    private String email;
    private ListaCircular<Cancion> cancionesFav= new ListaCircular<>();
}
