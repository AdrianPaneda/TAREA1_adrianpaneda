/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package herramientas;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Adrian Pañeda Hamadi
 */
public class Herramientas {
    
    /**
     * Metodo para verificar email 
     * @param email
     * @return boolean 
     */
    public static boolean verificarEmail(String email){
    
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches(); 
    }
    
    /**
     * Metodo para verificar que se introduce un nombre valido
     * @param nombre
     * @return boolean
     */
    public static boolean verificarNombre(String nombre){
        
        String regex = "^[a-zA-áéíóúÁÉÍÓÚñÑ]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(nombre);
        return matcher.matches();
    }
    
    /**
     * Metodo para verificar nombre de usuario
     * @param usuario
     * @return boolean
     */
    public static boolean verificarNombreusuario(String usuario){
    
    	String regex = "^[A-Za-z]{3,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(usuario);
        return matcher.matches();
    }
    /**
     * Metodo para verificar formato de contraseña
     * @param contrasena
     * @return boolean
     */
    public static boolean verificarContrasena(String contrasena) {
    	
    	String regex = "^[^\\s]{3,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(contrasena);
        return matcher.matches();
    }
    /**
     * Metodo para controlar las confirmaciones del menú
     * @param confirmacion
     * @return boolean
     */
    public static boolean confirmarRechazar(String confirmacion) {
    	
    	confirmacion = confirmacion.toLowerCase();
    	if(confirmacion.equals("s")||confirmacion.equals("n")) {
    		return true;
    	}
    	else return false;
    }
    
    
}
