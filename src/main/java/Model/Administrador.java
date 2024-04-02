package Model;

import lombok.*;

public class Administrador {
    private String username;
    private String contrasenia;

    public Administrador(){
        this.contrasenia="$aDmiN";
        this.username="admin";
    }

}
