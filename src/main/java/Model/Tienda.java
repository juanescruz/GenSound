package Model;

import Archivos.ArchivoUtils;
import Estructuras.Arbol.ArbolBinario;
import Estructuras.Lista.ListaIterador;
import Estructuras.ListaCircular.IteradorCircular;
import Estructuras.ListaCircular.ListaCircular;
import lombok.Data;

import Estructuras.Arbol.Nodo;
import Estructuras.Lista.ListaDoble;
import Estructuras.Lista.NodoDoble;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.random.RandomGenerator;

import Estructuras.Arbol.*;

import Estructuras.Arbol.*;

@Data
public class Tienda {
    private final HashMap<String, List<Cancion>> catalogo;
    private ArbolBinario artistas;
    private HashMap<String, Usuario> usuarios;
    private Administrador admin;

    private static Tienda Tienda;
    private static final Logger LOGGER = Logger.getLogger(Tienda.class.getName());


    public static Tienda getInstance(){
        if(Tienda == null){
            Tienda = new Tienda();
        }
        return Tienda;
    }
    private Tienda() {
        try {
            FileHandler fh = new FileHandler("logs.log", true);
            fh.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fh);
        } catch (IOException e) {
            LOGGER.log(Level.INFO, "Archivo no encontrado");
            LOGGER.log(Level.INFO, "Archivo no encontrado");
        }
        LOGGER.log(Level.INFO, "Se creó una nueva instancia");

        this.catalogo=new HashMap<>();
        
        this.usuarios=new HashMap<>();
        leerUsuarios();

        this.artistas= new ArbolBinario();
        this.admin=new Administrador();

    }

    private void leerUsuarios() {
        try {
            Object objeto = ArchivoUtils.deserializarObjeto("src/main/resources/Archivos/usuarios.bin");
            this.usuarios = (HashMap<String, Usuario>) objeto;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void escribirUsuarios() {
        try {
            ArchivoUtils.serializarObjeto("src/main/resources/Archivos/usuarios.bin", usuarios);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void agregarUsuarios(Usuario usuario){
        usuarios.put(usuario.getUsername(), usuario);
        escribirUsuarios();
    }

    /**
     * Este metodo lee el archivo artistas y con ello agrega los artistas y sus respectivas canciones
     * @param
     * @return
     */
    private void leerArtistasCanciones() throws Exception {
        boolean esArtista = false;
        try(Scanner scanner= new Scanner(new File("src/main/resources/Archivos/artistas"))){
            while(scanner.hasNextLine()){
                String linea= scanner.nextLine();
                if(linea.startsWith("#Artistas")){
                    esArtista = true;
                    linea= scanner.nextLine();
                }else{
                    esArtista = false;
                    linea= scanner.nextLine();
                }

                if(esArtista){
                    String [] valores= linea.split(";");
                    this.artistas.agregarArtista(new Artista(Integer.parseInt(valores[0]),valores[1],valores[2],Boolean.parseBoolean(valores[3]),null));

                }else{
                    String [] valores= linea.split(";");
                    guardarCancionArtista(valores);

                }
            }
        }catch(IOException e){
            LOGGER.log(Level.WARNING, e.getMessage());
        }

    }
    private void guardarCancionArtista(String[] valores) {
        artistas.buscarArtistaPorId(Integer.parseInt(valores[0]));

    }

    public boolean existeUsuario(String username){
       return usuarios.containsKey(username);
    }
    public Usuario getUser(String username){
        return usuarios.get(username);
    }
    public boolean contrasenaCorrecta(String username, String contrasena){
        if(usuarios.get(username).getContrasenia().equals(contrasena)){
            return true;
        }
        return false;
    }
    public boolean correoExiste(String correo){
        boolean flag= false;
        for(Map.Entry<String, Usuario>entry: usuarios.entrySet()){
            if(usuarios.get(entry).getEmail().equals(correo)){
                flag= true;
            }
        }
        return flag;
    }
    /*public void eliminarCancion(Usuario usuario, Cancion cancion){
        usuario.getCancionesFav().eliminar(cancion);

    }
    public void agregarCancion(Usuario usuario, Cancion cancion){
        usuario.getCancionesFav().agregarfinal(cancion);
    }
    public void ordenarCancionesAnio(Usuario usuario, Cancion cancion){

    }
<<<<<<< HEAD


=======
>>>>>>> parent of a3cc411 (Fix: Metodos de busqueda)
    /**
     * Este metodo recibe un nombre de un artista, lo busca y retorna la lista de canciones
     * @param nombre
     * @return
     */
    public List<Cancion> buscarArtista(String nombre) {
        Artista artista = buscarArtistaRec(artistas.getRaiz(), nombre);
        if (artista != null) {
            return (List<Cancion>) artista.getCanciones();
        } else {
            return null;
        }
    }

    /**
     * Este metodo busca el artista de manera recursiva en los sub arboles del arbol binario
     * @param nodo
     * @param nombre
     * @return
     */
    private Artista buscarArtistaRec(Nodo nodo, String nombre) {
        if (nodo == null || nodo.getArtista().getNombreArtista().equals(nombre)) {
            if (nodo != null) {
                return nodo.getArtista();
            } else {
                return null;
            }
        }
        if (nombre.compareTo(nodo.getArtista().getNombreArtista()) < 0) {
            return buscarArtistaRec(nodo.getIzquierdo(), nombre);
        } else {
            return buscarArtistaRec(nodo.getDerecho(), nombre);
        }
    }

    /**
     * Este metodo busca canciones con dos atributos, si hay coincidencia en cualquier atributo, retornara la cancion
     * implementa hilos para buscar en los subarboles
     * @param atributo1
     * @param atributo2
     * @return
     */
    public List<Cancion> buscarCancionesO(String atributo1, String atributo2) {
        List<Cancion> canciones = new ArrayList<>();
        Thread hiloIzquierdo = new Thread(() -> buscarCancionesORecursivo(artistas.getRaiz().getIzquierdo(), atributo1, atributo2, canciones));
        Thread hiloDerecho = new Thread(() -> buscarCancionesORecursivo(artistas.getRaiz().getDerecho(), atributo1, atributo2, canciones));

        hiloIzquierdo.start();
        hiloDerecho.start();

        try {
            hiloIzquierdo.join();
            hiloDerecho.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return canciones;
    }

    /**
     * este metodo busca canciones por ó recursivamente, compara en este caso el nombre de la cancion y el album
     * @param nodo
     * @param atributo1
     * @param atributo2
     * @param canciones
     */
    private void buscarCancionesORecursivo(Nodo nodo, String atributo1, String atributo2, List<Cancion> canciones) {
        if (nodo != null) {
            ListaDoble cancionesArtista = nodo.getArtista().getCanciones();
            NodoDoble<Cancion> nodoTemp = cancionesArtista.getNodoPrimero();
            while (nodoTemp != null) {
                Cancion cancion = nodoTemp.getValorNodo();
                if (cancion.getNombreCancion().equals(atributo1) || cancion.getNombreAlbum().equals(atributo2) || cancion.getNombreCancion().equals(atributo2) || cancion.getNombreAlbum().equals(atributo1)) {
                    synchronized (canciones) {
                        canciones.add(cancion);
                    }
                }
                nodoTemp = nodoTemp.getSiguienteNodo();
            }
            buscarCancionesORecursivo(nodo.getIzquierdo(), atributo1, atributo2, canciones);
            buscarCancionesORecursivo(nodo.getDerecho(), atributo1, atributo2, canciones);
        }
    }

    /**
     * Este metodo busca canciones con dos atributos, debe haber coincidencia en los dos atributos, retornara la o las canciones
     * implementa hilos para buscar en los subarboles
     * @param atributo1
     * @param atributo2
     * @return
     */
    public List<Cancion> buscarCancionesY(String atributo1, String atributo2) {
        List<Cancion> canciones = new ArrayList<>();
        Thread hiloIzquierdo = new Thread(() -> buscarCancionesYRecursivo(artistas.getRaiz().getIzquierdo(), atributo1, atributo2, canciones));
        Thread hiloDerecho = new Thread(() -> buscarCancionesYRecursivo(artistas.getRaiz().getDerecho(), atributo1, atributo2, canciones));

        hiloIzquierdo.start();
        hiloDerecho.start();

        try {
            hiloIzquierdo.join();
            hiloDerecho.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return canciones;
    }

    /**
     * este metodo busca canciones por ó recursivamente, compara en este caso el nombre de la cancion y el album
     * @param nodo
     * @param atributo1
     * @param atributo2
     * @param canciones
     */
    private void buscarCancionesYRecursivo(Nodo nodo, String atributo1, String atributo2, List<Cancion> canciones) {
        if (nodo != null) {
            ListaDoble cancionesArtista = nodo.getArtista().getCanciones();
            NodoDoble<Cancion> current = cancionesArtista.getNodoPrimero();
            while (current != null) {
                Cancion cancion = current.getValorNodo();
                if (cancion.getNombreCancion().equals(atributo1) && cancion.getNombreAlbum().equals(atributo2) || cancion.getNombreCancion().equals(atributo2) && cancion.getNombreAlbum().equals(atributo1)) {
                    synchronized (canciones) {
                        canciones.add(cancion);
                    }
                }
                current = current.getSiguienteNodo();
            }
            buscarCancionesYRecursivo(nodo.getIzquierdo(), atributo1, atributo2, canciones);
            buscarCancionesYRecursivo(nodo.getDerecho(), atributo1, atributo2, canciones);
        }
    }

    public void agregarCancion(Cancion cancion, String nombreArtista){

    }
    public void agregarArtista(Artista artista) throws Exception {
        artistas.agregarArtista(artista);
    }
}
