package Estructuras.Arbol;

import Model.Artista;
import lombok.Getter;

@Getter

public class Nodo {
    Artista artista;
    Nodo izquierdo;
    Nodo derecho;

    public Nodo(Artista artista) {
        this.artista = artista;
        this.derecho=null;
        this.izquierdo = null;
    }
}
