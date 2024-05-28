package Estructuras.ListaCircular;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListaCircularTest {

    @Test
    void deshacerEliminacion() {

        /*

        EL METODO INSERTAR() FALLA CUANDO SOLO SE INSERTA UN VALOR A LA LISTA, NO NECESARIAMENTE FALLA CON LA FUNCION DESHACER, FALLA SIEMPRE.

         */

        ListaCircular<Integer> listaCircular = new ListaCircular<>();
        // Inicializamos la lista con un elemento y lo eliminamos
        listaCircular.insertar(1); // Método ficticio para agregar
        listaCircular.insertar(2);

        listaCircular.borrar(2); // Método ficticio para eliminar

        listaCircular.deshacer(); // Llamamos al método deshacer

        assertTrue(listaCircular.contains(2)); // La lista debería contener el elemento 1

    }

    @Test
    void deshacerInsercion() {

        ListaCircular<Integer> listaCircular = new ListaCircular<>();
        // Inicializamos la lista e insertamos un elemento
        listaCircular.insertar(1); // Método ficticio para agregar

        listaCircular.insertar(2);

        // Se asume que agregar hace push de la operación a pilaDeshacer
        assertTrue(listaCircular.contains(2)); // Verificamos que la lista contiene el elemento 1

        listaCircular.deshacer(); // Llamamos al método deshacer

        assertFalse(listaCircular.contains(2)); // La lista no debería contener el elemento 1 después de deshacer

    }

    @Test
    void rehacerEliminacion() {

        ListaCircular<Integer> listaCircular = new ListaCircular<>();
        // Inicializamos la lista con un elemento, lo eliminamos y deshacemos la eliminación
        listaCircular.insertar(1); // Método ficticio para agregar
        listaCircular.insertar(2);

        listaCircular.borrar(2); // Método ficticio para eliminar
        listaCircular.deshacer(); // Deshacemos la eliminación

        assertTrue(listaCircular.contains(2)); // La lista debería contener el elemento 1 después de deshacer

        listaCircular.rehacer(); // Llamamos al método rehacer

        assertFalse(listaCircular.contains(2)); // La lista no debería contener el elemento 1 después de rehacer

    }

    @Test
    void rehacerInsercion() {

        ListaCircular<Integer> listaCircular = new ListaCircular<>();
        // Inicializamos la lista e insertamos un elemento, luego deshacemos la inserción
        listaCircular.insertar(1); // Método ficticio para agregar
        listaCircular.insertar(2);
        listaCircular.deshacer(); // Deshacemos la inserción

        // Se asume que deshacer mueve la operación a pilaRehacer
        assertFalse(listaCircular.contains(2)); // La lista no debería contener el elemento 1 después de deshacer

        listaCircular.rehacer(); // Llamamos al método rehacer

        assertTrue(listaCircular.contains(2)); // La lista debería contener el elemento 1 después de rehacer

    }
}