package Estructuras.Arbol;
import Estructuras.Arbol.ArbolBinario;
import Estructuras.Lista.ListaDoble;
import Model.Artista;
import Model.Cancion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ArbolBinarioTest {

    private ArbolBinario arbol;

    @BeforeEach
    public void setUp() {
        arbol = new ArbolBinario();
    }

    @Test
    public void testAgregarArtista() throws Exception {
        ListaDoble<Cancion> canciones1 = new ListaDoble<>();
        Artista artista1 = new Artista(1, "Artista 1", "Nacionalidad 1", false, canciones1);
        ListaDoble<Cancion> canciones2 = new ListaDoble<>();
        Artista artista2 = new Artista(2, "Artista 2", "Nacionalidad 2", false, canciones2);
        arbol.agregarArtista(artista1);
        arbol.agregarArtista(artista2);

        assertEquals(2, arbol.getTamanio());
    }

    @Test
    public void testBuscarArtistaPorId() throws Exception {
        ListaDoble<Cancion> canciones1 = new ListaDoble<>();
        Artista artista1 = new Artista(1, "Artista 1", "Nacionalidad 1", false, canciones1);
        ListaDoble<Cancion> canciones2 = new ListaDoble<>();
        Artista artista2 = new Artista(2, "Artista 2", "Nacionalidad 2", false, canciones2);
        arbol.agregarArtista(artista1);
        arbol.agregarArtista(artista2);

        Artista encontrado = arbol.buscarArtistaPorId(2);
        assertNotNull(encontrado);
        assertEquals("Artista 2", encontrado.getNombreArtista());
    }

    @Test
    public void testEliminarArtista() throws Exception {
        ListaDoble<Cancion> canciones1 = new ListaDoble<>();
        Artista artista1 = new Artista(1, "Artista 1", "Nacionalidad 1", false, canciones1);
        ListaDoble<Cancion> canciones2 = new ListaDoble<>();
        Artista artista2 = new Artista(2, "Artista 2", "Nacionalidad 2", false, canciones2);
        ListaDoble<Cancion> canciones3 = new ListaDoble<>();
        Artista artista3 = new Artista(3, "Artista 3", "Nacionalidad 3", false, canciones3);

        arbol.agregarArtista(artista1);
        arbol.agregarArtista(artista2);
        arbol.agregarArtista(artista3);

        arbol.eliminarArtista("Artista 2");

        assertEquals(2, arbol.getTamanio());
        assertNull(arbol.buscarArtistaPorId(2));
    }
}


