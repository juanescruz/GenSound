package Estructuras.ListaCircular;

import lombok.Getter;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Stack;

public class ListaCircular<T> implements Iterable<T>, Serializable {

    @Getter
    private Nodo<T> cabeza;
    private Nodo<T> nodoPrimero;
    private Nodo<T> nodoUltimo;
    @Getter
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

    public void setCabeza(Nodo<T> nuevaCabeza){
        this.cabeza = nuevaCabeza;
    }

    public void setTamanio(int tamanio){
        this.tamanio = tamanio;
    }

    /**
     * Deshace la última operación realizada en la lista, si hay alguna disponible en la pila de deshacer.
     */
    public void deshacer() {
        if (!pilaDeshacer.isEmpty()) {
            Deshacer<T> operacionDeshacer = pilaDeshacer.pop();
            operacionDeshacer.deshacer(this, pilaRehacer);
        }
    }
    /**
     * Rehace la última operación deshecha en la lista, si hay alguna disponible en la pila de rehacer.
     */
    public void rehacer(){
        if (!pilaRehacer.isEmpty()){
            Rehacer<T> operacionRehacer = pilaRehacer.pop();
            operacionRehacer.rehacer(this);
        }
    }

    /**
     * Inserta un nuevo nodo con el dato especificado al final de la lista circular.
     * @param dato El dato que se va a insertar en el nuevo nodo.
     */
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
    /**
     * Elimina el nodo que contiene el dato especificado de la lista circular.
     * @param dato El dato que se va a eliminar de la lista.
     */
    public void borrar(T dato){

        Nodo<T> previo = cabeza;
        boolean encontrado = false;

        //buscar el nodo previo
        do{

            if( previo.getSiguienteNodo().getValorNodo().equals(dato)){
                encontrado = true;
                break;
            }
            previo = previo.getSiguienteNodo();

        } while(previo!=cabeza);

        if(encontrado){

            Nodo<T> actual = previo.getSiguienteNodo();

            if(cabeza == cabeza.getSiguienteNodo()){
                cabeza = null;
            }else{
                previo.setSiguienteNodo(actual.getSiguienteNodo());

                if(actual==cabeza) {
                    cabeza = previo.getSiguienteNodo();
                }

            }

            tamanio--;

            pilaDeshacer.push(new Deshacer<>(previo, actual));
            pilaRehacer.clear();

            actual = null;

        }

    }

    /*public void borrar(T dato) {

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
    }*/
    /**
     * Comprueba si la lista circular contiene el valor especificado.
     *
     * @param value El valor que se va a buscar en la lista.
     * @return true si la lista contiene el valor especificado, false en caso contrario.
     */
    public boolean contains(T value) {
        if (cabeza == null) {
            return false;  // La lista está vacía
        }

        Nodo<T> actual = cabeza;
        do {
            if (actual.getValorNodo() == value) {
                return true;
            }
            actual = actual.getSiguienteNodo();
        } while (actual != cabeza);

        return false;
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


    /**
     * Verifica si el índice especificado es válido dentro de la lista.
     *
     * @param indice El índice a verificar.
     * @return true si el índice es válido, false en caso contrario.
     * @throws RuntimeException Si el índice no es válido.
     */
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

    /**
     * Obtiene el nodo en la posición especificada dentro de la lista.
     *
     * @param indice El índice del nodo a obtener.
     * @return El nodo en la posición especificada, o null si el índice está fuera del rango válido.
     */
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

        return new IteradorListaCircular(cabeza);
    }

    protected class IteradorListaCircular implements Iterator<T>{

        private Nodo<T> referencia;
        private Nodo<T> actual;
        private int posicion = 0;

        /**
         * Constructor de la clase IteradorListaCircular
         * @param referencia
         */
        public IteradorListaCircular(Nodo<T> referencia) {
            this.referencia = referencia;
            this.actual = referencia;
            this.posicion = 0;
        }

        @Override
        public boolean hasNext() {
            return actual!=null;
        }

        @Override
        public T next() {
            T aux = actual.getValorNodo();

            if(actual.getSiguienteNodo() == referencia){
                actual = null;
            }else{
                actual = actual.getSiguienteNodo();
            }
            posicion++;

            return aux;
        }

        /**
         * Posici�n actual de la lista
         * @return
         */
        public int getPosicion() {
            return posicion;
        }

    }


}

