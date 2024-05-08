package Estructuras.Arbol;
import Model.Artista;

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

    public Artista buscarArtistaPorId(int id) {
        return buscarArtistaPorIdRec(raiz, id);
    }

    private Artista buscarArtistaPorIdRec(Nodo nodo, int id) {
        if (nodo == null) {
            return null;
        }
        if (id == nodo.artista.getCodigoArtista()) {
            return (Artista) nodo.artista;
        }

        if (id < nodo.artista.getCodigoArtista()) {
            return buscarArtistaPorIdRec(nodo.izquierdo, id);
        } else {
            return buscarArtistaPorIdRec(nodo.derecho, id);
        }
    }
}