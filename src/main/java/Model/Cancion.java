package Model;


import Estructuras.Arbol.ArbolBinario;
import lombok.*;

@ToString
@Builder
@Setter
@Getter
@AllArgsConstructor
public class Cancion  {

    private int codigoCancion;
    private String nombreCancion;
    private String nombreAlbum;
    private int anio;
    private double duracion;
    private String genero;
    private String url;




}
