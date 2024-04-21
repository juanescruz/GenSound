package Model;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Cancion {
    private final String codigoCancion;
    private String nombreCancion;
    private String nombreAlbum;
    private int anio;
    private double duracion;
    private String genero;
    private String url;


}
