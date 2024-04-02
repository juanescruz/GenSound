package Estructuras.ListaCircular;

import java.util.Iterator;

    public class IteradorCircular<T> implements Iterator<T> {

    private Nodo<T> nodo;

    private int posicion;


    public IteradorCircular(Nodo<T> nodo) {
        this.nodo = nodo;
        this.posicion = 0;
    }

    @Override
    public boolean hasNext() {
        return nodo != null;
    }

    @Override
    public T next() {
        T valor = nodo.getValorNodo();
        nodo = nodo.getSiguienteNodo();
        posicion++;
        return valor;
    }
}