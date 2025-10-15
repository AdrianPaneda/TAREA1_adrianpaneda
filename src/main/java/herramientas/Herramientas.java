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
        
        String regex = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(nombre);
        return matcher.matches();
    }
}
