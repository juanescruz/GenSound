package Estructuras.Arbol;

import Model.Artista;

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
