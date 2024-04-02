package Estructuras.Lista;

import java.util.Iterator;

public class ListaIterador<E> implements Iterator<E> {

    private int indice;
    private NodoDoble<E> nodo;

    public ListaIterador(NodoDoble<E> nodo) {
        this.indice=0;
        this.nodo=nodo;
    }

    @Override
    public boolean hasNext() {
        return nodo!=null;
    }

    @Override
    public E next() {
        E valor= nodo.getValorNodo();
        nodo= nodo.getSiguienteNodo();
        indice++;
        return valor;
    }


    public boolean hasPrevious() {
        return nodo!=null;
    }

    public E Previous() {
        E valor= nodo.getValorNodo();
        nodo= nodo.getAnteriorNodo();
        indice++;
        return valor;
    }

    public int getIndice() {
        return indice;
    }



}
