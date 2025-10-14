/**
* Clase CircoMainClass.java
*
* @author Adrian Pañeda Hamadi
* @version 1.0
*/
package principal;
import entidades.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CircoMainClass {
	
	/**
	 * 
	 */
	public static Scanner leer = new Scanner(System.in);
	
	public static void menus(Sesion sesion) {
		
		try {
			
		switch(sesion.getPerfil()) {
		
	
		case null:
			
			 int opcion = 0;
				
				do {
						
				        System.out.println("1. Iniciar sesión");
				        System.out.println("2. Ver espectáculos");
				        System.out.println("3. Salir");
				        System.out.println("===========================================");
				        System.out.print("👉 Por favor, elige una opción (1-3): ");
				        
				        opcion = leer.nextInt();
				        
				        switch(opcion) {
				        
				        case 1: break;
				        
				        case 2: break;
				        
				        case 3: break;
				        
				        default: System.out.println("Opcion no válida, intentelo de nuevo");
				        
				        
				        }
				
				}while(opcion!=3);	
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			break;
		
		
		default: break;
		}
		
		
		
		
	}catch(InputMismatchException e) {System.out.println("Opcion no válida, intentelo de nuevo");}	
	}

	public static void main(String[] args) {
		
		System.out.println("\n*** 🎪 ¡BIENVENIDOS AL GRAN CIRCO! 🎪 ***");
		System.out.println("===========================================");
		System.out.println("===========================================");
		Sesion sesion = new Sesion();
		menus(sesion);
		
		
       
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

	}

}
