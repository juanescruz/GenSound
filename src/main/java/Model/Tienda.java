package Model;

import Archivos.ArchivoUtils;
import Estructuras.Arbol.ArbolBinario;
import Estructuras.Lista.ListaIterador;
import Estructuras.ListaCircular.IteradorCircular;
import Estructuras.ListaCircular.ListaCircular;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

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

        this.admin=new Administrador();

    }

    private void leerUsuarios() {
        try {
            Object objeto = ArchivoUtils.deserializarObjeto("src/main/resources/Archivos/usuarios");
            this.usuarios = (HashMap<String, Usuario>) objeto;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void escribirUsuarios() {
        try {
            ArchivoUtils.serializarObjeto("src/main/resources/Archivos/usuarios", usuarios);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void agregarUsuarios(Usuario usuario){
        usuarios.put(usuario.getUsername(), usuario);
        escribirUsuarios();
    }


    private void leerAristasCanciones() {
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
                    //this.artistas(new Artista());

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
        /*for (int i=0;i<artistas.getTamanio();i++){
            if(artistas.getArtista.getCodigoArtista.equals(valores[0]){
                Cancion cancion= new Cancion(valores[1],valores[2],valores[3],valores[4],valores[5],valores[6]);
                artistas.getArtista.getCanciones.agregarFinal(cancion);
            }
        }*/
    }
    public boolean existeUsuario(String username){
       return usuarios.containsKey(username);
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
*/
}
