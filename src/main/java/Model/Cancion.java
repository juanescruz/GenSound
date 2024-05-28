package Model;


import Estructuras.Arbol.ArbolBinario;
import lombok.*;

import java.io.Serializable;

@ToString
@Builder
@Setter
@Getter
@AllArgsConstructor
public class Cancion implements Serializable {

    private int codigoCancion;
    private String nombreCancion;
    private String nombreAlbum;
    private int anio;
    private double duracion;
    private String genero;
    private String url;
    private String caratula;



}
