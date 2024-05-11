package Model;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InicioSesion {

    private static InicioSesion instancia;
    private Usuario usuario;

    public static InicioSesion getInstance() {
        if (instancia == null) {
            System.out.println("Creando nueva instancia de SesionCliente");
            instancia = new InicioSesion();
        }
        return instancia;
    }

}
