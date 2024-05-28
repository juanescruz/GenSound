package Estructuras.Arbol;

import Model.Artista;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class Nodo implements Serializable {
    Artista artista;
    Nodo izquierdo;
    Nodo derecho;

    public Nodo(Artista artista) {
        this.artista = artista;
        this.derecho=null;
        this.izquierdo = null;
    }
}
