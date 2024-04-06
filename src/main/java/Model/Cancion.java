package Model;

import Estructuras.Arbol.ArbolBinario;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Setter
@Getter
public class Cancion  {
    private ArbolBinario artistas;
    private int codigoCancion;
    private String nombreCancion;
    private String nombreAlbum;
    private int anio;
    private double duracion;
    private String genero;
    private String url;




}
