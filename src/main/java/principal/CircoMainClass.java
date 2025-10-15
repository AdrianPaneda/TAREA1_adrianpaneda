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
import herramientas.Herramientas;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CircoMainClass {
	
	
	public static Scanner leer = new Scanner(System.in);
        
        /**
         * Metodo para recorrer nodos del xml paises
         * @param Nodelist nodos
         * @return List<String>
         */
        private static List<String> leerNodos(NodeList nodos){
        
          List<String> paises = new ArrayList<>();
          Node nodo;
            for (int i = 0; i < nodos.getLength(); i++) {
                
                //Asigno a la variable nodo el nodo pais
                nodo = nodos.item(i);
                NodeList nodosPais = nodo.getChildNodes();
                //Recorro todos los nodos pais
                for (int j = 0; j < nodosPais.getLength(); j++) {
                    //En cada pais busco el nodo con el nombre y lo añado a la lista
                    if(nodosPais.item(j).getNodeName().equals("nombre")){
                        
                     paises.add(nodosPais.item(j).getNodeValue());
                    
                    }
                }
    
            }
          
        return paises;
        
        
        }
        
        /**
         * Metodo para encontrar pais en xml con DOM 
         * @param pais
         * @param fichero
         * @return boolean
         */
        public static boolean encontrarPais(String pais, File fichero){
            
            boolean encontrado = false;
            
            try{
                
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc=builder.parse(fichero);
            Node raiz = doc.getFirstChild();
            NodeList paises = raiz.getChildNodes();
            List<String> listaPaises = leerNodos(paises);
            for(String a: listaPaises){
            
                if(a.equals(pais)){ 
                   encontrado = true;
                   break;
                }       
            
            }
            
             return encontrado;
            
            }
            catch(IOException e){
                System.out.println("Error al leer el archivo(compruebe que existe): "+e.getMessage());
            }
            catch(SAXException e){
                System.out.println("Error, el archivo XML no esta bien formado: "+e.getMessage());
            }
            catch(ParserConfigurationException e){
                System.out.println("Error al crear el parser: "+e.getMessage());
            }
            
            
        
        
        
        
       return encontrado;

        }
        
	
        public static void registrarPersona(){
            
            String verificar="";
            do{
            System.out.println("Bienvenido al sistema de registro");
            System.out.println("===========================================");
            System.out.println("/n Datos personales");
            
            //email
            String email;
            boolean verificarEmail;
            //Verificar email
            do{
                System.out.print("Introduzca el email a continuacion:");
                email=leer.nextLine();
                verificarEmail = Herramientas.verificarEmail(email);
                
                if(!verificarEmail){System.out.println("\s Lo siento, email no valido, intentelo de nuevo");}
                
            }while(!verificarEmail);
            
            //Nombre
            String nombre;
            boolean verificarNombre;
             do{
                System.out.print("Introduzca el nombre a continuacion:");
                nombre=leer.nextLine();
                verificarNombre = Herramientas.verificarNombre(nombre);
                
                if(!verificarNombre){System.out.println("\s Lo siento, nombre no valido, intentelo de nuevo");}
                
            }while(!verificarNombre);
            
            //Nacionalidad
            String nacionalidad;
            boolean verificarNacionalidad=false;
            File fichero = new File("resources/paises.xml");
            
            do{
              System.out.print("Introduzca la nacionalidad a continuacion:");
              nacionalidad=leer.nextLine(); 
              verificarNacionalidad = encontrarPais(nacionalidad,fichero);
              if(!verificarNacionalidad){System.out.println("\s Lo siento, paíd no registrado, intentelo de nuevo");}
                
            
            
            }while(!verificarNacionalidad);
     
            }while(!verificar.equals("si"));
        
        }
	
		
		

	public static void main(String[] args) {
		
	registrarPersona();
		
	}

}
