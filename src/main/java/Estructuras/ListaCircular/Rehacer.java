package Estructuras.ListaCircular;

import java.io.Serializable;

public class Rehacer<T> implements Serializable {
    private final Nodo<T> nodoAnterior;
    private final Nodo<T> nodoBorrado;

    Rehacer(Nodo<T> nodoAnterior, Nodo<T> nodoBorrado) {
        this.nodoAnterior = nodoAnterior;
        this.nodoBorrado = nodoBorrado;
    }

    public void rehacer(ListaCircular<T> listaCircular) {

        Nodo<T> actual = listaCircular.getCabeza();

        //Verifica si se ha deshecho una inserción para volver a insertar el dato
        if (nodoAnterior == null) {
            while (actual.getSiguienteNodo() != listaCircular.getCabeza()) {
                actual = actual.getSiguienteNodo();
            }
            actual.setSiguienteNodo(nodoBorrado);
            listaCircular.setTamanio(listaCircular.getTamanio() + 1);

        } else { //Si no se deshizo una eliminación para volver a eliminar el dato
            while (actual.getSiguienteNodo() != nodoBorrado) {
                actual = actual.getSiguienteNodo();
            }
            actual.setSiguienteNodo(nodoBorrado.getSiguienteNodo());
            listaCircular.setTamanio(listaCircular.getTamanio() - 1);
        }
    }


}
