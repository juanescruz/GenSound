package Estructuras.Arbol;

import Estructuras.Lista.ListaDoble;
import Estructuras.Lista.NodoDoble;
import Model.Artista;
import Model.Cancion;

import java.util.*;
public class ArbolBinario {
    private Nodo raiz;

    public ArbolBinario(){
        this.raiz=null;
    }
    public void agregarArtista(Artista artista) {
        raiz = agregarArtistaNodo(raiz, artista);
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

    public Artista buscarArtista(String nombre) {
        Nodo nodo = buscarRec(raiz, nombre);
        return (nodo != null) ? nodo.artista : null;
    }

    private Nodo buscarRec(Nodo nodo, String nombre) {
        if (nodo == null || nodo.artista.getNombreArtista().equals(nombre)) {
            return nodo;
        }

        if (nombre.compareTo(nodo.artista.getNombreArtista()) < 0) {
            return buscarRec(nodo.izquierdo, nombre);
        }
        else {
            return buscarRec(nodo.derecho, nombre);
        }
    }


    public List<Cancion> buscarCancionesO(String atributo1, String atributo2) {
        List<Cancion> canciones = new ArrayList<>();
        buscarCancionesORec(raiz, atributo1, atributo2, canciones);
        return canciones;
    }

    public List<Cancion> buscarCancionesO0(String atributo1, String atributo2) {

        List<Cancion> canciones = new ArrayList<>();
        Thread hiloIzquierdo = new Thread(() -> buscarCancionesORec(raiz.izquierdo, atributo1, atributo2, canciones));
        Thread hiloDerecho = new Thread(() -> buscarCancionesORec(raiz.derecho, atributo1, atributo2, canciones));

        hiloIzquierdo.start();
        hiloDerecho.start();

        try {
            hiloIzquierdo.join();
            hiloDerecho.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return canciones;
    }
    //Toma
    private void buscarCancionesORec(Nodo nodo, String atributo1, String atributo2, List<Cancion> canciones) {
        if (nodo != null) {
            ListaDoble cancionesArtista = nodo.artista.getCanciones();

            NodoDoble<Cancion> current = cancionesArtista.getNodoPrimero();
            while (current != null) {
                Cancion cancion = current.getValorNodo();
                if (cancion.getNombreCancion().equals(atributo1) || cancion.getNombreAlbum().equals(atributo2)) {
                    canciones.add(cancion);
                }
                current = current.getSiguienteNodo();
            }
            buscarCancionesORec(nodo.izquierdo, atributo1, atributo2, canciones);
            buscarCancionesORec(nodo.derecho, atributo1, atributo2, canciones);
        }
    }
    public List<Cancion> buscarCancionesY(String atributo1, String atributo2) {
        List<Cancion> canciones = new ArrayList<>();
        buscarCancionesYRec(raiz, atributo1, atributo2, canciones);
        return canciones;
    }
    private void buscarCancionesYRec(Nodo nodo, String atributo1, String atributo2, List<Cancion> canciones) {
        if (nodo != null) {
            ListaDoble cancionesArtista = nodo.artista.getCanciones();

            NodoDoble<Cancion> current = cancionesArtista.getNodoPrimero();
            while (current != null) {
                Cancion cancion = current.getValorNodo();
                if (cancion.getNombreCancion().equals(atributo1) && cancion.getNombreAlbum().equals(atributo2)) {
                    canciones.add(cancion);
                }
                current = current.getSiguienteNodo();
            }
            buscarCancionesYRec(nodo.izquierdo, atributo1, atributo2, canciones);
            buscarCancionesYRec(nodo.derecho, atributo1, atributo2, canciones);
        }
    }
}
