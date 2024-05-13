package Estructuras.ListaCircular;

import java.util.Iterator;

    public class IteradorCircular<T> implements Iterator<T> {

    private Nodo<T> nodo;


    public IteradorCircular(Nodo<T> nodo) {
        this.nodo = nodo;
    }

    @Override
    public boolean hasNext() {
        return nodo != null;
    }

    @Override
    public T next() {
        T valor = nodo.getValorNodo();
        nodo = nodo.getSiguienteNodo();
        return valor;
    }

}