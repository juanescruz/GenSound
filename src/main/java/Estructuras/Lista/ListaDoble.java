package Estructuras.Lista;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListaDoble<T> implements Iterable<T>, Serializable{
    private NodoDoble<T> nodoPrimero;
    private NodoDoble<T> nodoUltimo;
    private int tamanio;


    public ListaDoble() {
        nodoPrimero = null;
        nodoPrimero = null;
        tamanio = 0;
    }


    public boolean isEmpty(){
        if(nodoPrimero==null){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Agrega un nuevo nodo al inicio de la lista doblemente enlazada.
     *
     * @param valorNodo El valor del nodo a agregar.
     */
    public void agregarInicio(T valorNodo) {

        NodoDoble<T> nuevoNodo = new NodoDoble<>(valorNodo);

        if(estaVacia())
        {
            nodoPrimero = nodoUltimo = nuevoNodo;
        }
        else
        {
            nuevoNodo.setSiguienteNodo(nodoPrimero);
            nodoPrimero = nuevoNodo;
        }
        tamanio++;
    }

    /**
     * Agrega un nuevo nodo al final de la lista doblemente enlazada.
     * @param valorNodo El valor del nodo a agregar.
     */
    public void agregarfinal(T valorNodo) {

        NodoDoble<T> nuevoNodo = new NodoDoble<>(valorNodo);

        if(estaVacia())
        {
            nodoPrimero = nodoUltimo = nuevoNodo;
        }
        else
        {
            nodoUltimo.setSiguienteNodo(nuevoNodo);
            nuevoNodo.setAnteriorNodo(nodoUltimo);
            nodoUltimo = nuevoNodo;

        }
        tamanio++;
    }

    /**
    *Metodo para agregar un dato a la lista
    */
    public void agregar(T dato, int indice) {

        if(indiceValido(indice)) {

            if(indice==0) {
                agregarInicio(dato);
            }
            else {
                NodoDoble<T> nuevo = new NodoDoble<>(dato);
                NodoDoble<T> actual = obtenerNodo(indice);

                nuevo.setSiguienteNodo(actual);
                nuevo.setAnteriorNodo(actual.getAnteriorNodo());
                actual.getAnteriorNodo().setSiguienteNodo(nuevo);
                actual.setAnteriorNodo(nuevo);

                tamanio++;
            }
        }
    }
    /**
     * Obtiene todos los elementos almacenados en la lista y los devuelve como una lista.
     * @return Una lista que contiene todos los elementos almacenados en la lista doblemente enlazada.
     */
    public List<T> obtenerTodosLosElementos() {
        List<T> elementos = new ArrayList<>();
        NodoDoble<T> actual = nodoPrimero;
        while (actual != null) {
            elementos.add(actual.getValorNodo());
            actual = actual.getSiguienteNodo();
        }
        return elementos;
    }
    /**
     * Borra completamente la Lista
     */
    public void borrarLista() {
        nodoPrimero = nodoUltimo = null;
        tamanio = 0;
    }


    //Obtener Nodo el valor de un Nodo
    public T obtenerValorNodo(int indice) {

        NodoDoble<T> nodoTemporal = null;
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
     * Verifica si el índice proporcionado es válido para acceder a un elemento en la lista.
     * @param indice El índice a verificar.
     * @return true si el índice es válido, false de lo contrario.
     * @throws RuntimeException si el índice no es válido.
     */    private boolean indiceValido(int indice) {
        if( indice>=0 && indice<tamanio ) {
            return true;
        }
        throw new RuntimeException("�ndice no v�lido");
    }


    //Verificar si la lista esta vacia
    public boolean estaVacia() {
        //		return(nodoPrimero == null)?true:false;
        return nodoPrimero == null && nodoUltimo == null;
    }


    /**
     * Imprime en consola la lista enlazada
     */
    public void imprimirLista() {

        NodoDoble<T> aux = nodoPrimero;

        while(aux!=null) {
            System.out.print( aux.getValorNodo()+"\t" );
            aux = aux.getSiguienteNodo();
        }

        System.out.println();
    }

    public void imprimirAtras() {

        for(NodoDoble<T> aux = nodoUltimo; aux!=null; aux = aux.getAnteriorNodo()) {
            System.out.print( aux.getValorNodo()+"\t" );
        }
        System.out.println();

    }


    //Eliminar dado el valor de un nodo
    /**
     * Elimina un elemento de la lista
     * @param dato dato a eliminar
     * @return El dato que se elimina
     */
    public T eliminar(T dato) {

        NodoDoble<T> nodo = buscarNodo(dato);

        if(nodo!=null) {

            NodoDoble<T> previo = nodo.getAnteriorNodo();
            NodoDoble<T> siguiente = nodo.getSiguienteNodo();

            if(previo==null) {
                nodoPrimero = siguiente;
            }else {
                previo.setSiguienteNodo(siguiente);
            }

            if(siguiente==null) {
                nodoUltimo = previo;
            }else {
                siguiente.setAnteriorNodo(previo);
            }

            nodo=null;
            tamanio--;

            return dato;
        }

        throw new RuntimeException("El valor no existe");
    }


    //Elimina el primer nodo de la lista
    public T eliminarPrimero() {

        if( !estaVacia() ) {
            NodoDoble<T> nodoAux = nodoPrimero;
            T valor = nodoAux.getValorNodo();
            nodoPrimero = nodoAux.getSiguienteNodo();

            if(nodoPrimero==null) {
                nodoUltimo = null;
            }
            else
            {
                nodoPrimero.setAnteriorNodo(null);
            }

            tamanio--;
            return valor;
        }

        throw new RuntimeException("Lista vac�a");
    }


    public T eliminarUltimo() {

        if( !estaVacia() ) {
            T valor = nodoUltimo.getValorNodo();
            NodoDoble<T> prev = obtenerNodo(tamanio-2);
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


    /**
     * Devuelve el Nodo de una lista dada su posici�n
     * @param indice �ndice para obtener el Nodo
     * @return Nodo objeto
     */
    private NodoDoble<T> obtenerNodo(int indice) {

        if(indice>=0 && indice<tamanio) {

            NodoDoble<T> nodo = nodoPrimero;

            for (int i = 0; i < indice; i++) {
                nodo = nodo.getSiguienteNodo();
            }

            return nodo;
        }

        return null;
    }

    /**
     * Devuelve un nodo que contenga un dato espec�fico
     * @param dato Dato a buscar
     * @return Nodo
     */
    private NodoDoble<T> buscarNodo(T dato){

        NodoDoble<T> aux = nodoPrimero;

        while(aux!=null) {
            if(aux.getValorNodo().equals(dato)) {
                return aux;
            }
            aux = aux.getSiguienteNodo();
        }

        return null;
    }

    public void modificarNodo(int indice, T nuevo) {

        if( indiceValido(indice) ) {
            NodoDoble<T> nodo = obtenerNodo(indice);
            nodo.setValorNodo(nuevo);
        }

    }

    public int obtenerPosicionNodo(T dato) {

        int i = 0;

        for( NodoDoble<T> aux = nodoPrimero ; aux!=null ; aux = aux.getSiguienteNodo() ) {
            if( aux.getValorNodo().equals(dato) ) {
                return i;
            }
            i++;
        }

        return -1;
    }

    public T obtener(int indice) {

        if( indiceValido(indice) ) {
            NodoDoble<T> n = obtenerNodo(indice);

            if(n!=null) {
                return n.getValorNodo();
            }
        }

        return null;
    }

    public void imprimirHaciaAtras() {

        for(NodoDoble<T> aux = nodoUltimo; aux!=null; aux = aux.getAnteriorNodo()) {
            System.out.print( aux.getValorNodo()+"\t" );
        }
        System.out.println();

    }

    public NodoDoble<T> getNodoPrimero() {
        return nodoPrimero;
    }


    public void setNodoPrimero(NodoDoble<T> nodoPrimero) {
        this.nodoPrimero = nodoPrimero;
    }


    public int getTamanio() {
        return tamanio;
    }


    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }


    public NodoDoble<T> getNodoUltimo() {
        return nodoUltimo;
    }


    public void setNodoUltimo(NodoDoble<T> nodoUltimo) {
        this.nodoUltimo = nodoUltimo;
    }

    @Override
    public ListaIterador<T> iterator() {
        return new ListaIterador<>(nodoPrimero);
    }


}


