package Estructuras.ListaCircular;

import java.io.Serializable;
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
 //
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
        return nodoPrimero == null;
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

    //Elimina el primer nodo de la lista
    public T eliminarPrimero() {

        if( !estaVacia() ) {
            Nodo<T> n = nodoPrimero;
            T valor = n.getValorNodo();
            nodoPrimero = n.getSiguienteNodo();

            if(nodoPrimero==null) {
//				nodoUltimo = null;
            }

            tamanio--;
            return valor;
        }

        throw new RuntimeException("Lista vac�a");
    }


    public T eliminarUltimo() {

        if( !estaVacia() ) {
            T valor = nodoUltimo.getValorNodo();
            Nodo<T> prev = obtenerNodo(tamanio-2);
            nodoUltimo = prev;

            if(nodoUltimo==null) {
                nodoPrimero=null;
            }else {
                prev.setSiguienteNodo(null);
            }

            tamanio--;
            return valor;
        }

        throw new RuntimeException("Lista vac�a");
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
    public IteradorCircular<T> iterator() {
        return new IteradorCircular<>(nodoPrimero);
    }


}

