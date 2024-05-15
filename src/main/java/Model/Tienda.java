package Model;

import Archivos.ArchivoUtils;
import Estructuras.Arbol.ArbolBinario;
import lombok.Data;

import Estructuras.Arbol.Nodo;
import Estructuras.Lista.ListaDoble;
import Estructuras.Lista.NodoDoble;

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


        ListaCircular<Integer> integers = new ListaCircular<>();

        integers.insertar(1);
        integers.insertar(2);
        integers.insertar(3);

        int contador = 0;
        for (Integer numero : integers) {
            if (contador == integers.getTamanio()){
                break;
            }
            System.out.println(numero);
            contador++;
        }

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

        this.admin=new Administrador();
        leerArtistasCanciones();

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
                    System.out.println(imprimirArreglo(valores));
                    this.artistas.agregarArtista(new Artista(Integer.parseInt(valores[0]),valores[1],valores[2],Boolean.parseBoolean(valores[3]),new ListaDoble<>()));

                }else if (esCancion){
                    String [] valores= linea.split(";");
                    System.out.println(imprimirArreglo(valores));
                    guardarCancionArtista(valores);
                }

            }

        }catch(IOException e){
            LOGGER.log(Level.WARNING, e.getMessage());
        }

    }
    private void guardarCancionArtista(String[] valores) {
        Cancion cancion= new Cancion((int) Math.random(), valores[1], valores[2], Integer.parseInt(valores[3]),Double.parseDouble(valores[4]),valores[5],valores[6]);
        artistas.buscarArtistaPorId(Integer.parseInt(valores[0])).getCanciones().agregarfinal(cancion);

    }
    public String imprimirArreglo(String [] array){
        String aux="";
        for(int i=0; i<array.length; i++){
            aux +=""+array[i];
        }
        return aux;
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
    public void eliminarCancion(Usuario usuario, Cancion cancion){
        usuario.getCancionesFav().borrar(cancion);

    }
    public void agregarCancion(Usuario usuario, Cancion cancion){
        usuario.getCancionesFav().insertar(cancion);
        System.out.println(usuario.getCancionesFav().toString());
    }
    public void ordenarCancionesAnio(Usuario usuario, Cancion cancion){

    }
    public List<Cancion> buscarArtista(String nombre) {
        List<Cancion> cancionesArtista = new ArrayList<>();
        buscarArtistaRec(artistas.getRaiz(), nombre, cancionesArtista);
        return cancionesArtista;
    }

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
     * Este metodo busca canciones con dos atributos, si hay coincidencia en cualquier atributo, retornara la cancion
     * implementa hilos para buscar en los subarboles
     * @param atributo1
     * @param atributo2
     * @return
     */
    public List<Cancion> buscarCancionesO(String atributo1, String atributo2) {
        List<Cancion> canciones = new ArrayList<>();

        buscarCancionesORecursivo(artistas.getRaiz(), atributo1, atributo2, canciones);

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
     * Este método busca canciones que contienen al menos uno de los atributos en el nombre de la canción o el álbum,
     * explorando de manera recursiva el árbol de artistas.
     *
     * @param nodo      El nodo desde el cual se inicia la búsqueda.
     * @param atributo1 El primer atributo a buscar.
     * @param atributo2 El segundo atributo a buscar.
     * @param canciones La lista donde se agregan las canciones encontradas.
     */
    private void buscarCancionesORecursivo(Nodo nodo, String atributo1, String atributo2, List<Cancion> canciones) {
        if (nodo != null) {
            ListaDoble cancionesArtista = nodo.getArtista().getCanciones();
            NodoDoble<Cancion> current = cancionesArtista.getNodoPrimero();
            while (current != null) {
                Cancion cancion = current.getValorNodo();
                boolean contieneAtributo1 = cancion.getNombreCancion().contains(atributo1);
                boolean contieneAtributo2 = cancion.getNombreCancion().contains(atributo2);
                boolean contieneAtributoAlbum1 = cancion.getNombreAlbum().contains(atributo1);
                boolean contieneAtributoAlbum2 = cancion.getNombreAlbum().contains(atributo2);
                if ((contieneAtributo1 || contieneAtributo2) || (contieneAtributoAlbum1 || contieneAtributoAlbum2)) {
                    canciones.add(cancion);
                }
                current = current.getSiguienteNodo();
            }
            buscarCancionesORecursivo(nodo.getIzquierdo(), atributo1, atributo2, canciones);
            buscarCancionesORecursivo(nodo.getDerecho(), atributo1, atributo2, canciones);
        }
    }

    public List<Cancion> buscarCancionesY(String atributo1, String atributo2) {
        Set<Cancion> cancionesSet = new HashSet<>();
        buscarCancionesYRecursivo(artistas.getRaiz(), atributo1, atributo2, cancionesSet);

        List<Cancion> canciones = new ArrayList<>(cancionesSet);
        return canciones;
    }

    private void buscarCancionesYRecursivo(Nodo nodo, String atributo1, String atributo2, Set<Cancion> canciones) {
        if (nodo != null) {
            ListaDoble cancionesArtista = nodo.getArtista().getCanciones();
            NodoDoble<Cancion> current = cancionesArtista.getNodoPrimero();
            while (current != null) {
                Cancion cancion = current.getValorNodo();
                // Verificar si la canción contiene ambos atributos en el nombre de la canción y el álbum
                if (cancion.getNombreCancion().contains(atributo1) && cancion.getNombreAlbum().contains(atributo2)) {
                    canciones.add(cancion);
                }
                current = current.getSiguienteNodo();
            }
            buscarCancionesYRecursivo(nodo.getIzquierdo(), atributo1, atributo2, canciones);
            buscarCancionesYRecursivo(nodo.getDerecho(), atributo1, atributo2, canciones);
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
        System.out.println(artistas.toString());
    }

    public List<Cancion> obtenerCanciones(){
        return artistas.obtenerTodasLasCanciones();
    }
    public ArrayList<Artista> obtenerArtistas(){
        return artistas.preorderAr();
    }

    public String hallarGeneroMasRepetido(){
        Map<String, Integer> generos=new HashMap<>();
        String genero="";
        ArrayList<Artista> artistasAux= artistas.preorderAr();
        for(int i=0;i<artistasAux.size();i++){
            genero= hallarGeneroEnArtista(artistasAux.get(i));
            generos.put(genero, generos.getOrDefault(genero, 0)+1);
        }
        String generoMasRepetido= "  ";
        int aux=0;
        for(Map.Entry<String, Integer> entry: generos.entrySet()){
            if(entry.getValue()>aux){
                aux= entry.getValue();
                generoMasRepetido=entry.getKey();
            }
        }
        return generoMasRepetido;
    }

    public String hallarGeneroEnArtista(Artista a){
        Map<String, Integer> generos=new HashMap<>();
        String genero="";
        for(int i=0;i<a.getCanciones().getTamanio();i++){
            ListaIterador<Cancion> iterador=a.getCanciones().iterator();
            if(iterador.hasNext()){
                genero=iterador.next().getGenero();
                generos.put(genero, generos.getOrDefault(genero, 0)+1);
            }
        }

        String generoMasRepetido= "";
        int aux=0;
        for(Map.Entry<String, Integer> entry: generos.entrySet()){
            if(entry.getValue()>aux){
                aux= entry.getValue();
                generoMasRepetido=entry.getKey();
            }
        }
        return generoMasRepetido;
    }


}
