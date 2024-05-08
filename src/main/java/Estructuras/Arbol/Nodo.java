package Estructuras.Arbol;

import Model.Artista;
import lombok.Getter;

@Getter
public class Nodo<Artista> {
    Artista artista;
    Nodo<Artista> izquierdo;
    Nodo<Artista> derecho;

    public Nodo(Artista artista) {
        this.artista = artista;
        this.derecho=null;
        this.izquierdo = null;
    }
}
