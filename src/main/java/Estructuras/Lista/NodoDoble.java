package Estructuras.Lista;

import java.io.Serializable;

public class NodoDoble <T> implements Serializable {
    private NodoDoble<T> siguienteNodo;
    private NodoDoble<T> anteriorNodo;

    public NodoDoble<T> getAnteriorNodo() {
        return anteriorNodo;
    }

    public void setAnteriorNodo(NodoDoble<T> anteriorNodo) {
        this.anteriorNodo = anteriorNodo;
    }

    private T valorNodo;

    public NodoDoble(T valorNodo) {
        this.valorNodo = valorNodo;
    }

    public NodoDoble(T dato, NodoDoble<T> siguiente) {
        this.valorNodo = dato;
        this.siguienteNodo = siguiente;
    }


    //Metodos get y set de la clase Nodo

    public NodoDoble<T> getSiguienteNodo() {
        return siguienteNodo;
    }


    public void setSiguienteNodo(NodoDoble<T> siguienteNodo) {
        this.siguienteNodo = siguienteNodo;
    }


    public T getValorNodo() {
        return valorNodo;
    }


    public void setValorNodo(T valorNodo) {
        this.valorNodo = valorNodo;
    }
}
