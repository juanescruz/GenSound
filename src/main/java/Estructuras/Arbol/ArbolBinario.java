package Estructuras.Arbol;
import Estructuras.Lista.ListaDoble;
import Estructuras.Lista.ListaIterador;
import Model.Artista;

import Model.Cancion;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class ArbolBinario {
    private Nodo raiz;
    private int tamanio;

    public ArbolBinario(){
        this.raiz=null;
    }
    public void agregarArtista(Artista artista) throws Exception {
        raiz = agregarArtistaNodo(raiz, artista);
        tamanio++;
    }

    private Nodo agregarArtistaNodo(Nodo nodo, Artista artista) throws Exception {
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
            throw new Exception("El artista con este nombre ya existe");
        }
        return nodo;
    }

    public Artista buscarArtistaPorId(int id) {
        return buscarArtistaPorIdRec(raiz, id);
    }

    public ArrayList<Cancion>  preorderCan() {
        return preorderRecCan(raiz);
    }
    public ArrayList<Artista>  preorderAr() {
        return preorderRecAr(raiz);
    }

    public ArrayList<Cancion> obtenerCanciones(ListaDoble<Cancion> canciones){
        ArrayList<Cancion> songs= new ArrayList<>();
        ListaIterador<Cancion> iterador= canciones.iterator();
        while(iterador.hasNext()){
            songs.add(iterador.next());
        }
        return songs;
    }
    public ArrayList<Cancion> preorderRecCan(Nodo nodo) {
        ArrayList<Cancion> canciones= new ArrayList<>();
        if (nodo != null) {
            preorderRecCan(nodo.izquierdo);
            canciones.addAll(obtenerCanciones(nodo.getArtista().getCanciones()));
            preorderRecCan(nodo.derecho);
            canciones.addAll(obtenerCanciones(nodo.getArtista().getCanciones()));

        }
        return canciones;
    }
    public ArrayList<Artista> preorderRecAr(Nodo nodo) {
        ArrayList<Artista> artistas= new ArrayList<>();
        if (nodo != null) {
            preorderRecAr(nodo.izquierdo);
            artistas.add(nodo.izquierdo.getArtista());
            preorderRecAr(nodo.derecho);
            artistas.add(nodo.derecho.getArtista());

        }
        return artistas;
    }

    private Artista buscarArtistaPorIdRec(Nodo nodo, int id) {
        if (nodo == null) {
            return null;
        }

        if (id == nodo.artista.getCodigoArtista()) {
            return nodo.artista;
        }

        if (id < nodo.artista.getCodigoArtista()) {
            return buscarArtistaPorIdRec(nodo.izquierdo, id);
        } else {
            return buscarArtistaPorIdRec(nodo.derecho, id);
        }
    }


    public void eliminarArtista(String nombre) {
        raiz = eliminarArtistaRec(raiz, nombre);
    }

    private Nodo eliminarArtistaRec(Nodo nodo, String nombre) {
        if (nodo == null) {
            return null;
        }

        if (nombre.equals(nodo.artista.getNombreArtista())) {
            // Caso 1: El nodo a eliminar es una hoja (no tiene hijos)
            if (nodo.izquierdo == null && nodo.derecho == null) {
                return null;
            }
            // Caso 2: El nodo a eliminar tiene un solo hijo
            if (nodo.izquierdo == null) {
                return nodo.derecho;
            }
            if (nodo.derecho == null) {
                return nodo.izquierdo;
            }
            // Caso 3: El nodo a eliminar tiene dos hijos
            Nodo sucesor = encontrarSucesor(nodo.derecho);
            nodo.artista = sucesor.artista;
            nodo.derecho = eliminarArtistaRec(nodo.derecho, sucesor.artista.getNombreArtista());
            return nodo;
        }

        if (nombre.compareTo(nodo.artista.getNombreArtista()) < 0) {
            nodo.izquierdo = eliminarArtistaRec(nodo.izquierdo, nombre);
            return nodo;
        }
        nodo.derecho = eliminarArtistaRec(nodo.derecho, nombre);
        return nodo;
    }

    private Nodo encontrarSucesor(Nodo nodo) {
        Nodo actual = nodo;
        while (actual.izquierdo != null) {
            actual = actual.izquierdo;
        }
        return actual;
    }
}