package Estructuras.ListaCircular;

import lombok.Getter;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Stack;

public class ListaCircular<T> implements Iterable<T>, Serializable {

    private Nodo<T> cabeza;
    private Nodo<T> nodoPrimero;
    private Nodo<T> nodoUltimo;
    private int tamanio;
    private final Stack<Deshacer<T>> pilaDeshacer;
    private final Stack<Rehacer<T>> pilaRehacer;


    public ListaCircular() {
        this.nodoPrimero = null;
        this.nodoUltimo = null;
        this.cabeza = null;
        this.tamanio = 0;
        this.pilaDeshacer = new Stack<>();
        this.pilaRehacer = new Stack<>();
    }

    public Nodo<T> getCabeza(){
        return cabeza;
    }

    public void setCabeza(Nodo<T> nuevaCabeza){
        this.cabeza = nuevaCabeza;
    }

    public int getTamanio(){
        return tamanio;
    }

    public void setTamanio(int tamanio){
        this.tamanio = tamanio;
    }

    public void deshacer() {
        if (!pilaDeshacer.isEmpty()) {
            Deshacer<T> operacionDeshacer = pilaDeshacer.pop();
            operacionDeshacer.deshacer(this, pilaRehacer);
        }
    }

    public void rehacer(){
        if (!pilaRehacer.isEmpty()){
            Rehacer<T> operacionRehacer = pilaRehacer.pop();
            operacionRehacer.rehacer(this);
        }
    }

    // Método para insertar un elemento en la lista
    public void insertar(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (cabeza == null) {
            cabeza = nuevoNodo;
            cabeza.setSiguienteNodo(cabeza);
        } else {
            Nodo<T> actual = cabeza;
            while (actual.getSiguienteNodo() != cabeza) {
                actual = actual.getSiguienteNodo();
            }
            actual.setSiguienteNodo(nuevoNodo);
            nuevoNodo.setSiguienteNodo(cabeza);
        }
        tamanio++;

        pilaDeshacer.push(new Deshacer<>(null, nuevoNodo));
        pilaRehacer.clear();
    }

    public void borrar(T dato) {

        if (cabeza == null) {
            return;
        }

        Nodo<T> actual = cabeza;
        Nodo<T> anterior = null;

        do {
            if (actual.getValorNodo().equals(dato)) {

                if (anterior != null) {
                    anterior.setSiguienteNodo(actual.getSiguienteNodo());
                } else {
                    cabeza = actual.getSiguienteNodo();
                }
                tamanio--;

                pilaDeshacer.push(new Deshacer<>(anterior, actual));
                pilaRehacer.clear();

                return;
            }
            anterior = actual;
            actual = actual.getSiguienteNodo();

        } while (actual != cabeza);
    }

    //Obtener Nodo el valor de un Nodo
    public T obtenerValorNodo(int indice) {

        Nodo<T> nodoTemporal = null;
        int contador = 0;

        if(indiceValido(indice))
        {
            nodoTemporal = nodoPrimero;

            while (contador < indice) {

                nodoTemporal = nodoTemporal.getSiguienteNodo();
                contador++;
            }
        }

        if(nodoTemporal != null)
            return nodoTemporal.getValorNodo();
        else
            return null;
    }


    //Verificar si indice es valido
    private boolean indiceValido(int indice) {
        if( indice>=0 && indice<tamanio ) {
            return true;
        }
        throw new RuntimeException("�ndice no v�lido");
    }


    //Verificar si la lista esta vacia
    public boolean estaVacia() {
        return nodoPrimero != null;
    }


    public void imprimirLista() {
        if (cabeza == null) {
            System.out.println("La lista está vacía.");
            return;
        }
        Nodo<T> actual = cabeza;
        do {
            System.out.print(actual.getValorNodo() + " ");
            actual = actual.getSiguienteNodo();
        } while (actual != cabeza);
        System.out.println();
    }

    private Nodo<T> obtenerNodo(int indice) {

        if(indice>=0 && indice<tamanio) {

            Nodo<T> nodo = nodoPrimero;

            for (int i = 0; i < indice; i++) {
                nodo = nodo.getSiguienteNodo();
            }

            return nodo;
        }

        return null;
    }


    /**
     * Cambia el valor de un nodo dada su posici�n en la lista
     * @param indice posici�n donde se va a cambiar el dato
     * @param nuevo nuevo valor por el que se actualizar� el nodo
     */
    public void modificarNodo(int indice, T nuevo) {

        if( indiceValido(indice) ) {
            Nodo<T> nodo = obtenerNodo(indice);
            nodo.setValorNodo(nuevo);
        }

    }

    public int obtenerPosicionNodo(T dato) {

        int i = 0;

        for( Nodo<T> aux = nodoPrimero ; aux!=null ; aux = aux.getSiguienteNodo() ) {
            if( aux.getValorNodo().equals(dato) ) {
                return i;
            }
            i++;
        }

        return -1;
    }

    @Override
    public Iterator<T> iterator() {

        return new IteradorListaSimple(cabeza);
    }

    protected class IteradorListaSimple implements Iterator<T>{

        private Nodo<T> nodo;
        /**
         * -- GETTER --
         *  Posici�n actual de la lista
         *
         * @return posici�n
         */
        @Getter
        private int posicion;

        /**
         * Constructor de la clase Iterador
         * @param nodo Primer Nodo de la lista
         */
        public IteradorListaSimple(Nodo<T> nodo) {
            this.nodo = nodo;
            this.posicion = 0;
        }

        @Override
        public boolean hasNext() {
            return nodo!=null;
        }

        @Override
        public T next() {
            T valor = nodo.getValorNodo();
            nodo = nodo.getSiguienteNodo();
            posicion++;
            return valor;
        }

    }


}

