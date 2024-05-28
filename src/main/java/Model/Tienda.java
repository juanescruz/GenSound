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

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Data
public class Tienda {

    private ArbolBinario artistas;
    private HashMap<String, Usuario> usuarios;
    private Administrador admin;

    private static Tienda Tienda;
    private static final Logger LOGGER = Logger.getLogger(Tienda.class.getName());


    public static Tienda getInstance() {
        if(Tienda == null){
            try {
                Tienda = new Tienda();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return Tienda;
    }
    private Tienda() throws Exception {

        try {
            FileHandler fh = new FileHandler("logs.log", true);
            fh.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fh);
        } catch (IOException e) {
            LOGGER.log(Level.INFO, "Archivo no encontrado");
            LOGGER.log(Level.INFO, "Archivo no encontrado");
        }
        LOGGER.log(Level.INFO, "Se creó una nueva instancia");

        
        this.usuarios=new HashMap<>();
        leerUsuarios();

        this.artistas= new ArbolBinario();
        leerArtistas();

        this.admin=new Administrador();
        leerArtistasCanciones();


    }
    /**
     * Este método lee el usuarios de los artistas del archivo usuarios
     * @param
     * @return
     */
    private void leerUsuarios() {
        try {
            Object objeto = ArchivoUtils.deserializarObjeto("src/main/resources/Archivos/usuarios.bin");
            this.usuarios = (HashMap<String, Usuario>) objeto;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Este método escribe el HashMap de los usuarios del archivo usuarios
     * @param
     * @return
     */
    private void escribirUsuarios() {
        try {
            ArchivoUtils.serializarObjeto("src/main/resources/Archivos/usuarios.bin", usuarios);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void leerArtistas() {
        try {
            Object objeto = ArchivoUtils.deserializarObjeto("src/main/resources/Archivos/artistas.bin");
            this.artistas = (ArbolBinario) objeto;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void escribirArtistas() {
        try {
            ArchivoUtils.serializarObjeto("src/main/resources/Archivos/artistas.bin", artistas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Este método agrega al usuario dado por parámetro al HashMap usuarios
     * @param usuario objeto Usuario registrado
     * @return
     */
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
        boolean esCancion=false;
        try(Scanner scanner= new Scanner(new File("src/main/resources/Archivos/artistas"))){
            while(scanner.hasNextLine()){
                String linea= scanner.nextLine();
                if(linea.startsWith("#Artistas")){
                    esArtista = true;
                    linea= scanner.nextLine();
                }else if (linea.startsWith("#Canciones")){
                    esCancion=true;
                    esArtista = false;
                    linea= scanner.nextLine();
                }

                if(esArtista){
                    String [] valores= linea.split(";");
                    this.artistas.agregarArtista(new Artista(Integer.parseInt(valores[0]),valores[1],valores[2],Boolean.parseBoolean(valores[3]),new ListaDoble<>()));

                }else if (esCancion){
                    String [] valores= linea.split(";");
                    guardarCancionArtista(valores);
                }

            }

        }catch(IOException e){
            LOGGER.log(Level.WARNING, e.getMessage());
        }

    }

    /**
     * Este método crea una canción con los valores dados por parametro, y se la agrega al artista dado el el arreglo[0]
     * @param valores Arreglo con los parámetros para crear una cancion
     * @return
     */
    private void guardarCancionArtista(String[] valores) {
        Cancion cancion= new Cancion((int) (Math.random()*100), valores[1], valores[2], Integer.parseInt(valores[3]),Double.parseDouble(valores[4]),valores[5],valores[6], valores[7]);
        artistas.buscarArtistaPorId(Integer.parseInt(valores[0])).getCanciones().agregarfinal(cancion);


    }
    /**
     * Este método comprueba que exista el usuario dado su nombre de usuario
     * @param username nombre de usuario del usuario
     * @return false, si no existe, true, si existe
     */
    public boolean existeUsuario(String username){
       return usuarios.containsKey(username);
    }

    /**
     * Este método retorna un usuario dado sus nombre de usuario
     * @param username nombre de usuario del usuario
     * @return usuario que le corresponde el username
     */
    public Usuario getUser(String username){
        return usuarios.get(username);
    }

    /**
     * Este método comprueba que, dado un nombre de usuario, la contraseña dada
     * corresponde con la del usuario
     * @param username nombre de usuario del usuario, contrasena contraseña del usuario
     * @return false, si no es correcta, true, si es correcta
     */
    public boolean contrasenaCorrecta(String username, String contrasena){
        if(usuarios.get(username).getContrasenia().equals(contrasena)){
            return true;
        }
        return false;
    }
    /**
     * Este método elimina la cancion dada de la lista de favoritos de un usuario
     * @param usuario usuario de la tienda, cancion cancion del catalogo del usuario
     * @return
     */
    public void eliminarCancion(Usuario usuario, Cancion cancion){
        usuario.getCancionesFav().borrar(cancion);

    }
    /**
     * Este método agrega una cancion a la lista de canciones favoritas del usuario
     * @param usuario usuario de la tienda, cancion cancion del catalogo de la tienda
     * @return true, si se pudo agregar la canción, false, si no se pudo agregar la canción
     */
    public boolean agregarCancion(Usuario usuario, Cancion cancion){
        if (!InicioSesion.getInstance().getUsuario().getCancionesFav().contains(cancion)){
            usuario.getCancionesFav().insertar(cancion);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "La canción ya se encuentra en tu playlist.", "Información.", JOptionPane.INFORMATION_MESSAGE);
        }
        return false;
    }
    /**
     * Este método devuelve las canciones de un artista, dado su nombre
     * @param nombre nombre del artista
     * @return cancionesArtista canciones del artista

     */
    public List<Cancion> buscarArtista(String nombre) {
        List<Cancion> cancionesArtista = new ArrayList<>();
        buscarArtistaRec(artistas.getRaiz(), nombre, cancionesArtista);
        return cancionesArtista;
    }
    /**
     * Este método devuelve las canciones de un artista, dado su nombre
     * @param nombre nombre del artista, nodo nodo del arbol,
     * @return cancionesArtista canciones del artista
     */
    private void buscarArtistaRec(Nodo nodo, String nombre, List<Cancion> cancionesArtista) {
        if (nodo != null) {
            Artista artista = nodo.getArtista();
            if (artista != null && artista.getNombreArtista().equals(nombre)) {
                ListaDoble canciones = artista.getCanciones();
                if (canciones != null) {
                    NodoDoble<Cancion> current = canciones.getNodoPrimero();
                    while (current != null) {
                        cancionesArtista.add(current.getValorNodo());
                        current = current.getSiguienteNodo();
                    }
                }
            }
            buscarArtistaRec(nodo.getIzquierdo(), nombre, cancionesArtista);
            buscarArtistaRec(nodo.getDerecho(), nombre, cancionesArtista);
        }
    }

    /**
     * Este metodo se encarga de buscar las canciones que coincidan con los atributos enviados, pueden coincidir con al menos uno
     * @param atributos
     * @return
     */
    public List<Cancion> buscarCancionesO(String[] atributos) {
        Set<Cancion> cancionesSet = new HashSet<>();

        buscarCancionesORecursivo(artistas.getRaiz(), atributos, cancionesSet);

        Thread hiloIzquierdo = new Thread(() -> buscarCancionesORecursivo(artistas.getRaiz().getIzquierdo(), atributos, cancionesSet));
        Thread hiloDerecho = new Thread(() -> buscarCancionesORecursivo(artistas.getRaiz().getDerecho(), atributos, cancionesSet));

        hiloIzquierdo.start();
        hiloDerecho.start();

        try {
            hiloIzquierdo.join();
            hiloDerecho.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(cancionesSet);
    }

    private void buscarCancionesORecursivo(Nodo nodo, String[] atributos, Set<Cancion> canciones) {
        if (nodo != null) {
            ListaDoble cancionesArtista = nodo.getArtista().getCanciones();
            NodoDoble<Cancion> current = cancionesArtista.getNodoPrimero();
            while (current != null) {
                Cancion cancion = current.getValorNodo();
                for (String atributo : atributos) {
                    if (cancion.getNombreCancion().contains(atributo) || cancion.getNombreAlbum().contains(atributo) || cancion.getGenero().contains(atributo)) {
                        canciones.add(cancion);
                        break;
                    }
                }
                current = current.getSiguienteNodo();
            }
            buscarCancionesORecursivo(nodo.getIzquierdo(), atributos, canciones);
            buscarCancionesORecursivo(nodo.getDerecho(), atributos, canciones);
        }
    }
    
    /**
     * Este metodo se encarga de buscar las canciones, que coincidan con los atributos, deben coincidir todos
     * @param atributos
     * @return
     */
    public List<Cancion> buscarCancionesY(String[] atributos) {
        Set<Cancion> cancionesSet = new HashSet<>();
        buscarCancionesYRecursivo(artistas.getRaiz(), atributos, cancionesSet);

        return new ArrayList<>(cancionesSet);
    }

    private void buscarCancionesYRecursivo(Nodo nodo, String[] atributos, Set<Cancion> canciones) {
        if (nodo != null) {
            ListaDoble cancionesArtista = nodo.getArtista().getCanciones();
            NodoDoble<Cancion> current = cancionesArtista.getNodoPrimero();
            while (current != null) {
                Cancion cancion = current.getValorNodo();
                boolean todosAtributosCoinciden = true;
                for (String atributo : atributos) {
                    if (!(cancion.getNombreCancion().contains(atributo) || cancion.getNombreAlbum().contains(atributo) || cancion.getGenero().contains(atributo))) {
                        todosAtributosCoinciden = false;
                        break;
                    }
                }
                if (todosAtributosCoinciden) {
                    canciones.add(cancion);
                }
                current = current.getSiguienteNodo();
            }
            buscarCancionesYRecursivo(nodo.getIzquierdo(), atributos, canciones);
            buscarCancionesYRecursivo(nodo.getDerecho(), atributos, canciones);
        }
    }


    /**
     * este metodo busca el id del artista dado, y se agrega la cancion a su lista de canciones
     * @param cancion
     * @param codArtista
     */
    public void agregarCancion(Cancion cancion, int codArtista) throws Exception {
        Artista artista =artistas.buscarArtistaPorId(codArtista);
        if(artista==null){
            throw new Exception("Artista no encontrado");
        }
        System.out.println(artista.toString());
        artista.getCanciones().agregarfinal(cancion);


    }
    /**
     * este metodo agrega un artista dado por parámetro al arbol binario de artistas que hay en la tienda
     * @param artista
     */
    public void agregarArtista(Artista artista) throws Exception {
        if(artistas.buscarArtistaPorId(artista.getCodigoArtista())!=null){
            throw new Exception("El artista ya se encuentra agregado");
        }
        artistas.agregarArtista(artista);
        escribirArtistas();
        System.out.println(artistas.toString());
    }

    public List<Cancion> obtenerCanciones(){
        return artistas.obtenerTodasLasCanciones();
    }
    public ArrayList<Artista> obtenerArtistas(){
        return artistas.preorderAr();
    }

    public String hallarGeneroMasRepetido() {
        List<Cancion> canciones = obtenerCanciones();
        Map<String, Integer> conteoGeneros = new HashMap<>();
        for (Cancion cancion : canciones) {
            String genero = cancion.getGenero();
            conteoGeneros.put(genero, conteoGeneros.getOrDefault(genero, 0) + 1);
        }
        String generoMasRepetido = null;
        int maximoConteo = 0;
        for (Map.Entry<String, Integer> entry : conteoGeneros.entrySet()) {
            if (entry.getValue() > maximoConteo) {
                generoMasRepetido = entry.getKey();
                maximoConteo = entry.getValue();
            }
        }
        return generoMasRepetido;
    }

    public Artista hallarArtistaMasPopular() throws NullPointerException{

        Artista artistaAux= new Artista();
        List<Cancion> canciones= obtenerCancionesUs();
        if(canciones==null){
            throw new NullPointerException("No hay ninguna canción likeada");
        }

        System.out.println("Canciones:");
        System.out.println(canciones);

        Map<Artista, Integer> conteoArtistas = new HashMap<>();
        for (Cancion cancion : canciones) {
            artistaAux = hallarArtistaCancion(cancion);

            System.out.println("Artista Aux:");
            System.out.println(artistaAux);
            conteoArtistas.put(artistaAux, conteoArtistas.getOrDefault(artistaAux, 0) + 1);
        }

        System.out.println( conteoArtistas );
        Artista artistaMasRepetido = null;
        int maximoConteo = 0;
        for (Map.Entry<Artista, Integer> entry : conteoArtistas.entrySet()) {
            if (entry.getValue() > maximoConteo) {
                artistaMasRepetido = entry.getKey();
                maximoConteo = entry.getValue();
            }
        }
        return artistaMasRepetido;
    }
    public List<Cancion> obtenerCancionesUs(){
        List<Cancion> canciones= new ArrayList<>();
        for(Usuario user: usuarios.values()){
            int contador = 0;
            for (Cancion cancion : user.getCancionesFav()) {
                if(contador == user.getCancionesFav().getTamanio()){
                    break;
                }else{
                    canciones.add(cancion);
                }
                contador++;
            }
        }
        return canciones;
    }
    /**
     * Este metodo halla el artista al que le corresponda la canción dada por parámetro
     * @param cancion cancion a la que se le desea hallar el artista
     * @return artista artsita al que le corresponda la canción
     */
    public Artista hallarArtistaCancion(Cancion cancion){
        Artista artista= new Artista();
        ArrayList<Artista> arts= artistas.preorderAr();

        System.out.println("Artistas ArbolPre:");
        System.out.println(arts);

        for(Artista ar:arts){
            ListaIterador<Cancion> iterador= ar.getCanciones().iterator();
            while(iterador.hasNext()){
                if(cancion.getNombreCancion().equals(iterador.next().getNombreCancion())){
                    artista=ar;
                    break;
                }
            }
        }
        return artista;
    }
}
