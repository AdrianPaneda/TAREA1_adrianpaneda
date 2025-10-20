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
import herramientas.PropertiesClass;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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
        public static Map<String,String> listarPaises(File fichero){
                System.out.println("**Listado de paises**");
                Map<String,String> listaPaises = new TreeMap<>();
                 try{
                
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setIgnoringComments(true);
                factory.setIgnoringElementContentWhitespace(true);
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(fichero);
                Node raiz = doc.getFirstChild();
                System.out.println(raiz.getNodeName());
                NodeList paises = raiz.getChildNodes();
                
               
                listaPaises= leerNodos(paises);
                
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
                 return listaPaises;
 
        }
        /**
         * Metodo para buscar persona 
         * @param email
         * @param nombreusuario
         * @return boolean
         */
        public static boolean buscarPersona(String email,String nombreusuario) {
        	
        	boolean repetido=false;
        	try(BufferedReader br = new BufferedReader(new FileReader(PropertiesClass.obtenerPropiedad("credenciales")))){
        	
        		String linea;
        		String[]datos;
        		while((linea=br.readLine())!= null) {
        		datos=linea.split("\\|");
        		if(datos[1].equals(nombreusuario)) {
        			repetido=true;
        			
        		}else if(datos[3].equals(email)) {	
        		repetido=true;
        		}
        		}       		
        	}catch(IOException e) {}
        	
        	return repetido;
        	
        }
        
       
        /**
         * Metodo para generar id autoincrement
         * @param tipo
         * @return int
         */
        public static int generarId(String tipo) {
            int contador = 1;
            try (BufferedReader br = new BufferedReader(new FileReader(PropertiesClass.obtenerPropiedad("credenciales")))) {
                String linea;

                while ((linea = br.readLine()) != null) {
                    String[] datos = linea.split("\\|");
                    if (datos.length > 6) {
                        switch (tipo.toLowerCase()) {
                            case "artista":
                            case "coordinacion":
                                if (datos[6].equalsIgnoreCase(tipo)) {
                                    contador++;
                                }
                                break;
                            case "persona":
                                contador++;
                                break;
                            default:
                                System.out.println("Error: " + tipo);
                                break;
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Fichero no encontrado");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            return contador;
        }
        
        public static LocalDate verificarFecha(String fecha) {
        	  LocalDate date=null;
        	  DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
         	  while(date == null) {	                      		 
         		  try {
         		  date = LocalDate.parse(fecha, formato);
         		  }
                           catch(DateTimeParseException e){
         			  System.out.println("\\t Fecha no válida,intentelo de nuevo");	                        			  
                           }	  	
        }
         	  return date;
        }

        /**
         * Metodo para registrar persona
         * @param
         */
        public static void registrarPersona(){
            
                System.out.println("Bienvenido al sistema de registro 👤");
                System.out.println("===========================================");
                System.out.println("---Datos personales---");
                boolean verificar=false;
                String respuesta;
                Persona persona;
                //email
                String email;
                //Verificar email
                do{
                    System.out.print("Introduzca el email a continuacion:");
                    email=leer.nextLine();
                    verificar = Herramientas.verificarEmail(email);
                    if(!verificar){System.out.println("\t Lo siento, email no valido, intentelo de nuevo");}
                }while(!verificar);

                //Nombre
                String nombre;
                 do{
                    System.out.print("Introduzca el nombre a continuacion:");
                    nombre=leer.nextLine();
                    verificar = Herramientas.verificarNombre(nombre);

                    if(!verificar){System.out.println("\t Lo siento, nombre no valido.Solo se admiten letras, intentelo de nuevo");}

                }while(!verificar);

                //Nacionalidad
                String idNacionalidad;
                String nacionalidad="";
                File fichero = new File(PropertiesClass.obtenerPropiedad("paises"));
                Map<String,String>paises = listarPaises(fichero);
                System.out.println("");   
                do{
                  System.out.print("Introduzca el id del país de nacionalidad a continuacion:");
                  idNacionalidad=leer.nextLine().toUpperCase(); 
                  verificar = encontrarPais(idNacionalidad,fichero);
                  if(!verificar){System.out.println("\t Error, país no registrado, intentelo de nuevo");}
                  else {    		
                		 nacionalidad = paises.get(idNacionalidad);
                  }   
                }while(!verificar);

                //Credenciales
                 System.out.println("--Credenciales--");
                //Nombre de usuario
                String nombreUsuario;
                do {                    
                    System.out.print("Introduzca el nombre de usuario a continuacion:");
                    nombreUsuario = leer.nextLine();
                    verificar = Herramientas.verificarNombreusuario(nombreUsuario);
                    if(!verificar) {System.out.println("\t Error, nombre de usuario no valido, intentelo de nuevo");}
   
                } while (!verificar);
               // Contraseña
                String password;
                do {                    
                    System.out.print("Introduzca la contraseña continuacion:");
                    password = leer.nextLine();
                    verificar = Herramientas.verificarContrasena(password);
                    if(!verificar) {System.out.println("\t Contraseña no válida(Minimo 3 caracteres sin espacios en blanco), intentelo de nuevo");}
   
                } while (!verificar);   
                //AsignarPerfil
                //Perfiles perfil = asignarPerfil();
                boolean perfilValido=false;
                String perfilConsola;
                String apodo=null;
                Especialidades especialidad;
                LocalDate fechaSenior=null;
                Perfiles perfil= null;
                List<String> especialidades = new ArrayList<>();
                do {
                    System.out.print("Introduzca el perfil de la persona (Coordinacion/Artista) a continuacion: ");    
                    perfilConsola = leer.nextLine().toLowerCase();
                    if(perfilConsola.equalsIgnoreCase(Perfiles.coordinacion.name())) {
                        perfil = Perfiles.coordinacion;
                        perfilValido=true;
                        boolean senior=false;
                        do {
                        System.out.println("Es senior? (s/n)");
                        respuesta=leer.nextLine();
                        verificar = Herramientas.confirmarRechazar(respuesta);
                        if(!verificar) {
                            System.out.println("\t Respuesta no valida, intentelo de nuevo");
                        } else { 
                            if(respuesta.equalsIgnoreCase("s")) {
                              senior=true;
                          	  String fecha;
                          	  DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
                          	  while(fechaSenior == null) {
                          		  
                          		  System.out.print("Introduzca desde que fecha (dd/MM/yyyy): ");
                          		  fecha = leer.nextLine();                      		 
                          		  try {
                          		  fechaSenior = LocalDate.parse(fecha, formato);
                          		  }
                                            catch(DateTimeParseException e){
                          			  
                          			  System.out.println("\\t Fecha no válida,intentelo de nuevo");	                        			  
                          		  }	  
                          	  }		
                            }    
                        } 	
                        }while(!verificar);
                        Credenciales credenciales = new Credenciales(nombreUsuario,password,perfil);
                        Coordinacion coordinador = new Coordinacion(email,nombre,idNacionalidad,credenciales,senior,fechaSenior);
                        persona = coordinador;
                  
                    } else if(perfilConsola.equalsIgnoreCase(Perfiles.artista.name())) {
                        perfil = Perfiles.artista;
                        perfilValido=true;
                        do {
                            System.out.println("Tiene apodo? (s/n)");
                            respuesta = leer.nextLine();
                            verificar = Herramientas.confirmarRechazar(respuesta);
                            if(!verificar) {
                                System.out.println("\\t Respuesta no valida, intentelo de nuevo");
                            } else { 
                                if(respuesta.equalsIgnoreCase("s")) {
                                    System.out.println("Introduzca el apodo a continuacion por favor");
                                    apodo = leer.nextLine();
                                }    
                            }
                        } while(!verificar);                                             
                        Credenciales credenciales = new Credenciales(nombreUsuario,password,perfil);
                        Artista artista = new Artista(email,nombre,idNacionalidad,credenciales,apodo);
                        persona = artista;
                        //Especialidades
                        int especialidadNum;
                        boolean encontrado;
                        System.out.println("--Especialidades--");
                        for (int i = 0; i < Especialidades.values().length; i++) {
                            System.out.println((i+1)+"-"+Especialidades.values()[i]);    
                        }
                        System.out.println("--Instrucciones--");
                        System.out.println("-Puede introducir cuantas especialidades desee (minimo una), además no se deberán repetir especialidades");
                        System.out.println("-Para añadir una especialidad introduzca uno de los valores asignados a cada especialidad ej: 1");
                        System.out.println("-Para finalizar la seleccion de especialidades simplemente introduzca un valor fuera del rango de especialidades.");
                        do {
                        	encontrado =false;
                        	verificar=false;
                            
                            System.out.print("Introduzca la especialidad a continuacion: ");
                            try {
                                especialidadNum = leer.nextInt();
                                leer.nextLine();
                                if(especialidadNum>0 && especialidadNum<=Especialidades.values().length) {
                                	especialidad=Especialidades.values()[especialidadNum-1];
                                    for (Especialidades e  : artista.getEspecialidad()) {
									if(especialidad==e) {
										System.out.println("\t Especialidad repetida, intentelo de nuevo por favor.");
										encontrado=true;
									}	
									}
                                    if(encontrado==false) {
                                   	
                                    artista.getEspecialidad().add(especialidad);
                                    System.out.println("\t Añadida especialidad: "+especialidad.toString());
                                    }   
                                } else {
                                    verificar = true;
                                    System.out.println("\t Seleccion de especialidades finalizada.");
                                }
                            } catch(InputMismatchException e) {
                                System.out.println("Introduzca un número por favor");
                                leer.nextLine();
                                verificar = false;
                            }
                        } while(!verificar);
    
                    } else {
                        System.out.println("\\t Perfil no válido, intentelo de nuevo por favor");
                    }
                } while(!perfilValido);

                // Verificar registro
                System.out.println("Estos son los datos que ha introducido:");
                System.out.println("Email: "+email);
                System.out.println("Nombre: "+nombre);
                System.out.println("Nacionalidad: "+idNacionalidad);
                System.out.println("Nombre de usuario: "+nombreUsuario);
                System.out.println("Nacionalidad: "+nacionalidad);
               

                do {
                    System.out.println("Desea continuar con el registro? Introduzca (s) para confirmar o (n) para cancelar el registro");
                    respuesta = leer.nextLine();
                    verificar = Herramientas.confirmarRechazar(respuesta);
                    if(!verificar) {System.out.println("\t Respuesta no valida, intentelo de nuevo");}
                } while (!verificar);
                if(respuesta.equals("s")) {
                	boolean repetido = false;
                	repetido = buscarPersona(email, nombreUsuario);
                	int id = generarId("persona");
                	if(repetido==false) {
                	try(BufferedWriter bw = new BufferedWriter(new FileWriter(PropertiesClass.obtenerPropiedad("credenciales"),true))){
                		String credencial = id+"|"+nombreUsuario+"|"+password+"|"+email+"|"+nombre+"|"+nacionalidad+"|"+perfil.toString()+"\n";
                		bw.write(credencial);
                		System.out.println("Persona registrada con exito");
                	
                	}catch(IOException e) {System.out.println(e.getMessage());}
                }
                
                }else {System.out.println("Registro interrumpido");}
        }
        
        public static String iniciarSesion(String nombreUsuario,String password) {
        	
        	String linea;
        	String[]datos;
        	String perfil="";
        	try(BufferedReader br = new BufferedReader(new FileReader(PropertiesClass.obtenerPropiedad("credenciales")))){
        		
        	while((linea=br.readLine())!=null) {
        	datos=linea.split("\\|");
        	if(nombreUsuario.equals(datos[1])&&password.equals(datos[2])) {
        	System.out.println("\t Sesion iniciada con exito");
        	System.out.println("Bienvenido "+nombreUsuario+ " que desea hacer?");
        	perfil=datos[6];
        	}	
        	}		
        	}
        	catch(FileNotFoundException e) {System.out.println("Fichero no encontrado"+e.getMessage());}
        	catch(IOException e) {}
        	
        return perfil;	
        }
        public static void crearEspectaculo(String perfil) {
        	 String nombre;
        	 boolean verificar=false;
             do{
                System.out.print("Introduzca el nombre del espectaculo continuacion:");
                nombre=leer.nextLine();
                if(nombre.length()>0&&nombre.length()<=25){
                verificar=true;		
                }
                if(!verificar){
                	System.out.println("\t Lo siento, nombre no valido.Solo se admiten desde 1 hasta 25 caracteres");
                	}

            }while(!verificar);
             
             String fechaIni;
             String fechaFin;
             LocalDate fechaInicio;
             LocalDate fechaFinal;
             do {
             System.out.print("Introduzca la fecha de inicio a continuacion(dd/mm/yyy): ");
             fechaIni=leer.nextLine();
             fechaInicio = verificarFecha(fechaIni);
             System.out.print("Introduzca la fecha de inicio a continuacion(dd/mm/yyy): ");
             fechaFin=leer.nextLine();
             fechaFinal = verificarFecha(fechaFin);
             // Sacamos la diferencia entre fechas
             Period diferencia = Period.between(fechaInicio, fechaFinal);
             if(diferencia.getYears()>1) { 
            	 verificar=false;
            	 System.out.println("\t Error la diferencia dentre la fecha de inicio y la final no puede superar el año.");		 
             }
             
             }while(!verificar);
             
             Espectaculo
             espectaculo = new Espectaculo(nombre,fechaInicio,fechaFinal);
        	
        }
        public static void  crearModificarEspectaculos(String perfil) {
        
        	int eleccion=0;
        	do {
        		try {
        	System.out.println("1-Crear nuevo espectaculo");	
        	System.out.println("2-Modificar espectaculo existente");
        	eleccion = leer.nextInt();
        	leer.nextLine();
        	switch(eleccion) {
        	case 1:crearEspectaculo(perfil); break;
        	case 2: break;
        	default: System.out.println("Opcion no valida, intentelo de nuevo");
        	}
        		}catch(InputMismatchException e) {
        			System.out.println("\t Opcion no valida, intentelo de nuevo");
        			leer.nextLine();
        		}
        		
        	}while(eleccion!=3);
        }
        
        public static void gestionarEspectaculos(String perfil) {
        	
        	System.out.println("--Gestion de espectaculos--");
        	int eleccion=0;
        	do {
        		try {
        	System.out.println("1-Crear o modificar espectaculo");	
        	System.out.println("2-Crear o modificar número");
        	System.out.println("3-Asignar artistas");
        	System.out.println("4-Salir de la gestion de espectaculos");
        	eleccion=leer.nextInt();
        	leer.nextLine();
        	switch(eleccion) {
        	
        	case 1:crearModificarEspectaculos(perfil); break;
        	case 2:System.out.println("\t Funcionalidad no disponible,se implementará en las proximas actualizaciones.Disculpe las molestias."); break;
        	case 3:System.out.println("\t Funcionalidad no disponible,se implementará en las proximas actualizaciones.Disculpe las molestias."); break;
        	case 4:System.out.println("\t Gestion de espectaculos finalizada."); break;

        	}	
        		}catch(InputMismatchException e) {
        			System.out.println("Opcion no valida, intentelo de nuevo");
        			leer.nextLine();
        			}	
        	}while(eleccion!=3);	
        }
	public static void main(String[] args) {
		
	// registrarPersona();
	System.out.println("\n*** 🎪 ¡BIENVENIDOS AL GRAN CIRCO! 🎪 ***");
	int eleccion=0;
	String usuario;
	String password;
	boolean encontrado;
	String perfil;
	Sesion sesion = new Sesion();
	do {
		try {
	    System.out.println("--Perfil invitado");
		System.out.println("1-Visualizar espectaculos");
		System.out.println("2-Log in");
		System.out.print("Introduzca una opcion a continuacion: ");
		eleccion=leer.nextInt();
		leer.nextLine();
	
		switch(eleccion) {
			
			case 1: break;
			
			
			case 2:
				System.out.print("Introduzca el nombre de usuario a continuacion: ");
				usuario = leer.nextLine();
				System.out.print("Introduzca la contraseña a continuacion: ");
				password=leer.nextLine();
				encontrado=buscarPersona(password, usuario);
				if(encontrado) {
				perfil=iniciarSesion(usuario, password);
				if(perfil.equals(Perfiles.artista.toString())) {
					//Iniciamos la sesion
					sesion.setNombre(usuario);
					sesion.setPerfil(Perfiles.artista);
					do {
						try {
					System.out.println("1-Ver ficha");
					System.out.println("2-Ver espectaculo completo");
					System.out.println("3-Log out");
					eleccion=leer.nextInt();
					switch(eleccion) {
						case 1:System.out.println("\t Funcionalidad no disponible,se implementará en las proximas actualizaciones.Disculpe las molestias."); break;
						case 2:System.out.println("\t Funcionalidad no disponible,se implementará en las proximas actualizaciones.Disculpe las molestias."); break; 
						case 3:
							sesion=new Sesion();
							System.out.println("\t Logged out"); break;
						default: System.out.println("\t Opcion no válida, intentalo de nuevo");break;
						}					
						}catch(InputMismatchException e) {System.out.println("\t Opcion no válida, intentelo de nuevo");}
						
					}while(eleccion!=3);
					eleccion=0;
				}else if(perfil.equals(Perfiles.coordinacion.toString())){
					
					sesion.setNombre(usuario);
					sesion.setPerfil(Perfiles.coordinacion);
					do {
						try {
							
							System.out.println("1-Ver espectaculo completo");
							System.out.println("2-Gestionar espectaculos");
							System.out.println("3-Log out");
							eleccion=leer.nextInt();
							switch(eleccion) {
							case 1:System.out.println("\t Funcionalidad no disponible,se implementará en las proximas actualizaciones.Disculpe las molestias."); break; 
							case 2:gestionarEspectaculos(perfil.toString()); break; 
							case 3: 
								sesion=new Sesion();
								System.out.println("Logged out");
								break;
							}
					
						}catch(InputMismatchException e) {System.out.println("\t Opcion no válida, intentelo de nuevo");}
						
				}while(eleccion!=3);
					eleccion=0;
				
				}
				
				}else if(usuario.equals(PropertiesClass.obtenerPropiedad("usuarioAdmin").toString())
						&&password.equals(PropertiesClass.obtenerPropiedad("contraseñaAdmin").toString())){
					sesion.setNombre(PropertiesClass.obtenerPropiedad("usuarioAdmin").toString());
					System.out.println("---ADMINISTRADOR---");
					do {	
					System.out.println("1-Registrar persona");
					System.out.println("2--Gestionar espectaculos");
					System.out.println("3-Log out");
					eleccion=leer.nextInt();
					leer.nextLine();
					
					switch(eleccion) {
					case 1: registrarPersona(); break;
					case 2: gestionarEspectaculos(PropertiesClass.obtenerPropiedad("usuarioAdmin"));
					case 3:
						sesion=new Sesion();
						System.out.println("\t Logged out");
					}
					
					}while(eleccion!=3);
					eleccion=0;
				}
		}
		}catch(InputMismatchException e) {System.out.println("\t Opcion no válida, intentelo de nuevo");
		leer.nextLine();
		}
	}while(eleccion!=3);

		
	}

}
