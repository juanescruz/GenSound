package Estructuras.Arbol;
import Estructuras.Lista.ListaDoble;
import Estructuras.Lista.ListaIterador;
import Model.Artista;

import Model.Cancion;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ArbolBinario implements Serializable {
    private Nodo raiz;
    private int tamanio;

    public ArbolBinario(){
        this.raiz=null;
    }
    public void agregarArtista(Artista artista) throws Exception {
        raiz = agregarArtistaNodo(raiz, artista);
        tamanio++;
    }

    private Nodo agregarArtistaNodo(Nodo nodo, Artista artista) {
        if (nodo == null) {
            return new Nodo(artista);
        }

        if (artista.getCodigoArtista() < nodo.artista.getCodigoArtista()) {
            nodo.izquierdo = agregarArtistaNodo(nodo.izquierdo, artista);
        } else if (artista.getCodigoArtista() > nodo.artista.getCodigoArtista()) {
            nodo.derecho = agregarArtistaNodo(nodo.derecho, artista);
        }
        return nodo;
    }

    public ArrayList<Cancion>  preorderCan() {
        return preorderRecCan(raiz);
    }
    public ArrayList<Artista>  preorderAr() {
        return preorderRecAr(raiz, new ArrayList<>());
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
        ArrayList<Cancion> canciones = new ArrayList<>();
        if (nodo != null) {
            preorderRecCan(nodo.izquierdo);
            preorderRecCan(nodo.derecho);
            canciones.addAll(obtenerCanciones(nodo.getArtista().getCanciones()));
        }
        return canciones;
    }

    public List<Cancion> obtenerTodasLasCanciones() {
        List<Cancion> canciones = new ArrayList<>();
        obtenerTodasLasCancionesRec(raiz, canciones);
        return canciones;
    }

    private void obtenerTodasLasCancionesRec(Nodo nodo, List<Cancion> canciones) {
        if (nodo != null) {
            obtenerTodasLasCancionesRec(nodo.getIzquierdo(), canciones);

            Artista artista = nodo.getArtista();
            if (artista != null && artista.getCanciones() != null) {
                canciones.addAll(artista.getCanciones().obtenerTodosLosElementos());
            }
            obtenerTodasLasCancionesRec(nodo.getDerecho(), canciones);


        }
    }
    public ArrayList<Artista> preorderRecAr(Nodo nodo, ArrayList<Artista> artistas) {
        if (nodo == null) {
            return artistas;
        }else{
            artistas.add( nodo.getArtista() );
            if (nodo.izquierdo != null) {
                preorderRecAr(nodo.izquierdo, artistas);
            }
            if (nodo.derecho != null) {
                preorderRecAr(nodo.derecho, artistas);
            }
        }
        return artistas;
    }


    public Artista buscarArtistaPorId(int id) {System.out.println("");
        return buscarArtistaPorIdRec(raiz, id);

    }

    private Artista buscarArtistaPorIdRec(Nodo nodo, int id) {
        if (nodo == null || nodo.artista.getCodigoArtista() == id) {
            return nodo != null ? nodo.artista : null;
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
                tamanio--;
                return null;
            }
            // Caso 2: El nodo a eliminar tiene un solo hijo
            if (nodo.izquierdo == null) {
                tamanio--;
                return nodo.derecho;
            }
            if (nodo.derecho == null) {
                tamanio--;
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
        } else {
            nodo.derecho = eliminarArtistaRec(nodo.derecho, nombre);
        }
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