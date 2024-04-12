package Model;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cancion {
    private int codigoCancion;
    private String nombreCancion;
    private String nombreAlbum;
    private int anio;
    private double duracion;
    private String genero;
    private String url;


}
