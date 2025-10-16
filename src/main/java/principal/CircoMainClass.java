/**
* Clase CircoMainClass.java
*
* @author Adrian Pañeda Hamadi
* @version 1.0
*/
package principal;
import entidades.*;
import java.util.Scanner;
import herramientas.Herramientas;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.TreeMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CircoMainClass {
	
	
	public static Scanner leer = new Scanner(System.in);
        
        /**
         * Metodo para recorrer nodos del xml paises
         * @param nodos
         * @return Map<Integer,String>
         */
        public static Map<String,String> leerNodos(NodeList nodos){
        
           Map<String,String> paises = new TreeMap<>();
           Node nodo;
            for (int i = 0; i < nodos.getLength(); i++) {  
                nodo = nodos.item(i);             
                //Compruebo que el nodo es pais y añado al Map
                if(nodo.getNodeName().equals("pais")){
                Element pais = (Element) nodo;
                String id = pais.getElementsByTagName("id").item(0).getTextContent();
                String nombre = pais.getElementsByTagName("nombre").item(0).getTextContent();
                paises.put(id, nombre);               
                }
                }
            return paises;
            }        
         
       
        
        /**
         * Metodo para encontrar pais en xml con DOM 
         * @param idPais
         * @param fichero
         * @return boolean
         */
        public static boolean encontrarPais(String idPais, File fichero){
            
            boolean encontrado = false;
            
            try{
                
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setIgnoringComments(true);
                factory.setIgnoringElementContentWhitespace(true);
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc=builder.parse(fichero);
                Node raiz = doc.getFirstChild();
                NodeList paises = raiz.getChildNodes();
                Map<String,String> listaPaises= leerNodos(paises);
                
                for (Map.Entry<String, String> entry : listaPaises.entrySet()) {
                if(entry.getKey().equals(idPais)){               
                encontrado = true;
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
        	
        /**
         * Metodo para listar paises
         * @param fichero 
         */
        public static void listarPaises(File fichero){
                System.out.println("**Listado de paises**");
                 try{
                
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setIgnoringComments(true);
                factory.setIgnoringElementContentWhitespace(true);
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(fichero);
                Node raiz = doc.getFirstChild();
                System.out.println(raiz.getNodeName());
                NodeList paises = raiz.getChildNodes();
                
               
                Map<String,String> listaPaises= leerNodos(paises);
                
                for (Map.Entry<String, String> entry : listaPaises.entrySet()) {
                    System.out.println(entry.getKey()+"-"+entry.getValue());    
                }   
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
 
        }
        
        /**
         * Metodo para registrar persona
         * @param
         */
        public static void registrarPersona(){
            
            boolean verificar=false;
            do{
                System.out.println("Bienvenido al sistema de registro");
                System.out.println("===========================================");
                System.out.println("/n ---Datos personales---");

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
                String nacionalidad="S";
                boolean verificarNacionalidad=false;
                File fichero = new File("src\\main\\java\\resources\\paises.xml");
                listarPaises(fichero);
                System.out.println("");
                boolean idCorrecto=true;
                do{
                  System.out.print("Introduzca el id del país de nacionalidad a continuacion:");
                  try{
                  nacionalidad=leer.nextLine();
                  }catch(InputMismatchException e){
                      System.out.println("Introduzca un id valido por favor;");
                      idCorrecto=false;
                  }
                  if(idCorrecto){
                  verificarNacionalidad = encontrarPais(nacionalidad,fichero);
                  if(!verificarNacionalidad){System.out.println("\s Lo siento, paíd no registrado, intentelo de nuevo");}
                }
                }while(!verificarNacionalidad);

                //Credenciales
                 System.out.println("--Credenciales--");
                //Nombre de usuario
                boolean verificarusuario=false;
                do {                    
                    System.out.print("Introduzca el nombre de usuario a continuacion:"); 
                    
                } while (verificarusuario);
               
                 
            
            
     
            }while(!verificar);
        
        }
	
		
		

	public static void main(String[] args) {
		
	registrarPersona();
		
	}

}
