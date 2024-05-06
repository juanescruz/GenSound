package Estructuras.Arbol;
import Estructuras.Arbol.Nodo;
import Estructuras.Lista.ListaDoble;
import Estructuras.Lista.NodoDoble;
import Estructuras.Lista.ListaDoble;
import Estructuras.Lista.NodoDoble;
import Model.Artista;
import Model.Cancion;

import java.util.*;

import lombok.Getter;

@Getter
public class ArbolBinario {
    private Nodo raiz;
    private int tamanio;

    public ArbolBinario(){
        this.raiz=null;
    }
    public void agregarArtista(Artista artista) {
        raiz = agregarArtistaNodo(raiz, artista);
        tamanio++;
    }

    private Nodo agregarArtistaNodo(Nodo nodo, Artista artista) {
        if (nodo == null) {
            return new Nodo(artista);
        }

        if (artista.getNombreArtista().compareTo(nodo.artista.getNombreArtista()) < 0) {
            nodo.izquierdo = agregarArtistaNodo(nodo.izquierdo, artista);
        }
        else if (artista.getNombreArtista().compareTo(nodo.artista.getNombreArtista()) > 0) {
            nodo.derecho = agregarArtistaNodo(nodo.derecho, artista);
        }
        // Si el artista ya existe en el arbol entonces:
        else {
            //Hay que poner que se hace, aca se ignora
        }
        return nodo;
    }
}