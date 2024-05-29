package Estructuras.ListaCircular;

import java.io.Serializable;
import java.util.Stack;

public class Deshacer<T> implements Serializable {
    private final Nodo<T> nodoAnterior;
    private final Nodo<T> nodoBorrado;

    Deshacer(Nodo<T> nodoAnterior, Nodo<T> nodoBorrado) {
        this.nodoAnterior = nodoAnterior;
        this.nodoBorrado = nodoBorrado;
    }

    public void deshacer(ListaCircular<T> listaCircular, Stack<Rehacer<T>> pilaRehacer){

//        System.out.println("Se hace push a la pila rehacer");
//
//        pilaRehacer.push(new Rehacer<>(nodoAnterior, nodoBorrado));
//
//        System.out.println("Se terminó se pushear.");

        if (nodoAnterior == null){
            listaCircular.borrar(nodoBorrado.getValorNodo());
        } else {

            if (listaCircular.getCabeza() == null){
                listaCircular.setCabeza(nodoBorrado);
                listaCircular.getCabeza().setSiguienteNodo(listaCircular.getCabeza());
                return;
            }

            deshacerEliminacion(listaCircular);
        }
    }

    public void deshacerEliminacion(ListaCircular<T> listaCircular) {

        // Restaurar el nodo eliminado nuevamente a la lista
        if (nodoAnterior == null) {

            // Si el nodo eliminado era el primer nodo
            listaCircular.setCabeza(nodoBorrado);

            //Reconectar la lista circular
            Nodo<T> ultimoNodo = listaCircular.getCabeza();

            while (ultimoNodo.getSiguienteNodo() != null) {
                ultimoNodo = ultimoNodo.getSiguienteNodo();
            }
            ultimoNodo.setSiguienteNodo(listaCircular.getCabeza());

        } else {
            // Si el nodo eliminado no era el primer nodo
            nodoAnterior.setSiguienteNodo(nodoBorrado);
        }

        listaCircular.setTamanio(listaCircular.getTamanio() + 1);
    }
}
