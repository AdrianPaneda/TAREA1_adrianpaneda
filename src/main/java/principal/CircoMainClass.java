/**
* Clase CircoMainClass.java
*
* @author Adrian Pañeda Hamadi
* @version 1.0
*/
package principal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import entidades.Artista;
import entidades.Credenciales;
import entidades.Especialidades;
import entidades.Espectaculo;
import entidades.Perfiles;
import entidades.Sesion;
import herramientas.Herramientas;
import herramientas.PropertiesClass;

public class CircoMainClass {

	public static Scanner leer = new Scanner(System.in);

	/**
	 * Metodo para recorrer nodos del xml paises
	 * 
	 * @param nodos
	 * @return Map<Integer,String>
	 */
	public static Map<String, String> leerNodos(NodeList nodos) {

		Map<String, String> paises = new TreeMap<>();
		Node nodo;
		for (int i = 0; i < nodos.getLength(); i++) {
			nodo = nodos.item(i);
			// Compruebo que el nodo es pais y añado al Map
			if (nodo.getNodeName().equals("pais")) {
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
	 * 
	 * @param idPais
	 * @param fichero
	 * @return boolean
	 */
	public static boolean encontrarPais(String idPais, File fichero) {

		boolean encontrado = false;

		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringComments(true);
			factory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(fichero);
			Node raiz = doc.getFirstChild();
			NodeList paises = raiz.getChildNodes();
			Map<String, String> listaPaises = leerNodos(paises);

			for (Map.Entry<String, String> entry : listaPaises.entrySet()) {
				if (entry.getKey().equals(idPais)) {
					encontrado = true;
				}
			}
			return encontrado;
		} catch (IOException e) {
			System.out.println("Error al leer el archivo(compruebe que existe): " + e.getMessage());
		} catch (SAXException e) {
			System.out.println("Error, el archivo XML no esta bien formado: " + e.getMessage());
		} catch (ParserConfigurationException e) {
			System.out.println("Error al crear el parser: " + e.getMessage());
		}
		return encontrado;
	}

	/**
	 * Metodo para listar paises
	 * 
	 * @param fichero
	 */
	public static Map<String, String> listarPaises(File fichero) {
		System.out.println("**Listado de paises**");
		Map<String, String> listaPaises = new TreeMap<>();
		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringComments(true);
			factory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(fichero);
			Node raiz = doc.getFirstChild();
			System.out.println(raiz.getNodeName());
			NodeList paises = raiz.getChildNodes();

			listaPaises = leerNodos(paises);

			for (Map.Entry<String, String> entry : listaPaises.entrySet()) {
				System.out.println(entry.getKey() + "-" + entry.getValue());
			}
		} catch (IOException e) {
			System.out.println("Error al leer el archivo(compruebe que existe): " + e.getMessage());
		} catch (SAXException e) {
			System.out.println("Error, el archivo XML no esta bien formado: " + e.getMessage());
		} catch (ParserConfigurationException e) {
			System.out.println("Error al crear el parser: " + e.getMessage());
		}
		return listaPaises;

	}

	/**
	 * Metodo para buscar persona
	 * 
	 * @param email
	 * @param nombreusuario
	 * @return boolean
	 */
	public static boolean buscarPersona(String password, String nombreusuario) {

		boolean encontrado = false;
		try (BufferedReader br = new BufferedReader(new FileReader(PropertiesClass.obtenerPropiedad("credenciales")))) {

			String linea;
			String[] datos;
			while ((linea = br.readLine()) != null) {
				datos = linea.split("\\|");
				if (datos[1].equals(nombreusuario)) {
					encontrado = true;

				} else if (datos[2].equals(password)) {
					encontrado = true;
				}
			}
			if (nombreusuario.equals(PropertiesClass.obtenerPropiedad("usuarioAdmin"))
					&& password.equals(PropertiesClass.obtenerPropiedad("contraseñaAdmin"))) {
				encontrado = true;
			}
		} catch (IOException e) {
		}

		return encontrado;

	}

	/**
	 * Metodo para generar id autoincrement
	 * 
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
						System.out.println("Error: Tipo no valido(" + tipo + ")");
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

	/**
	 * Metodo para parsear fecha
	 * 
	 * @param fecha
	 * @return
	 */
	public static LocalDate parseFecha(String fecha) {
		LocalDate date = null;
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		date = LocalDate.parse(fecha, formato);
		return date;
	}

	/**
	 * Metodo para ver si el email esta repetido
	 * 
	 * @param email
	 * @return boolean
	 */
	public static boolean emailEncontrado(String email) {

		boolean encontrado = false;
		try (BufferedReader br = new BufferedReader(new FileReader(PropertiesClass.obtenerPropiedad("credenciales")))) {

			String linea;
			String[] datos;
			while ((linea = br.readLine()) != null) {
				datos = linea.split("\\|");
				if (datos[3].trim().equals(email)) {

					encontrado = true;

				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("Fichero no encontrado");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return encontrado;
	}

	/**
	 * Metodo para ver si el email esta repetido
	 * 
	 * @param email
	 * @return boolean
	 */
	public static boolean nombreUsuarioEncontrado(String nombreUsuario) {

		boolean repetido = false;
		try (BufferedReader br = new BufferedReader(new FileReader(PropertiesClass.obtenerPropiedad("credenciales")))) {

			String linea;
			String[] datos;
			while ((linea = br.readLine()) != null) {
				datos = linea.split("\\|");
				if (datos[1].equals(nombreUsuario)) {

					repetido = true;

				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("Fichero no encontrado");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return repetido;
	}

	/**
	 * Metodo para registrar persona
	 * 
	 * @param
	 */
	public static void registrarPersona() {

		System.out.println("Bienvenido al sistema de registro 👤");
		System.out.println("===========================================");
		System.out.println("---Datos personales---");
		boolean verificar = false;
		String respuesta;

		// email
		String email;
		// Verificar email
		do {
			System.out.print("Introduzca el email a continuacion: ");
			email = leer.nextLine();

			if (!Herramientas.verificarEmail(email)) {
				System.out.println("\t Formato de email no válido, intentelo de nuevo");
				verificar = false;
			} else if (emailEncontrado(email)) {
				System.out.println("\t  este email ya está registrado, intentelo de nuevo");
				verificar = false;
			} else {
				verificar = true;
			}
		} while (!verificar);

		// Nombre
		String nombre;
		do {
			System.out.print("Introduzca el nombre a continuacion:");
			nombre = leer.nextLine();
			verificar = Herramientas.verificarNombre(nombre);

			if (!verificar) {
				System.out.println("\t Lo siento, nombre no valido.Solo se admiten letras, intentelo de nuevo");
			}
		} while (!verificar);

		// Nacionalidad
		String idNacionalidad;
		String nacionalidad = "";
		File fichero = new File(PropertiesClass.obtenerPropiedad("paises"));
		Map<String, String> paises = listarPaises(fichero);
		System.out.println("");
		do {
			System.out.print("Introduzca el id del país de nacionalidad a continuacion:");
			idNacionalidad = leer.nextLine().toUpperCase();
			verificar = encontrarPais(idNacionalidad, fichero);
			if (!verificar) {
				System.out.println("\t Error, país no registrado, intentelo de nuevo");
			} else {
				nacionalidad = paises.get(idNacionalidad);
			}
		} while (!verificar);

		// Credenciales
		System.out.println("--Credenciales--");
		// Nombre de usuario
		String nombreUsuario;
		do {
			System.out.print("Introduzca el nombre de usuario a continuacion: ");
			nombreUsuario = leer.nextLine();

			if (!Herramientas.verificarNombreusuario(nombreUsuario)) {
				System.out.println("\t Introduzca un nombre de al menos 3 caracteres, intentelo de nuevo");
				verificar = false;
			} else if (nombreUsuarioEncontrado(nombreUsuario)) {
				System.out.println("\t este nombre de usuario ya está registrado, intentelo de nuevo");
				verificar = false;
			} else {
				verificar = true;
			}

		} while (!verificar);

		// Contraseña
		String password;
		do {
			System.out.print("Introduzca la contraseña continuacion:");
			password = leer.nextLine();
			verificar = Herramientas.verificarContrasena(password);
			if (!verificar) {
				System.out.println(
						"\t Contraseña no válida(Minimo 3 caracteres sin espacios en blanco), intentelo de nuevo");
			}

		} while (!verificar);
		boolean perfilValido = false;
		String perfilConsola;
		String apodo = null;
		Especialidades especialidad;
		LocalDate fechaSenior = null;

		Perfiles perfil = null;
		do {
			System.out.print("Introduzca el perfil de la persona (Coordinacion/Artista) a continuacion: ");
			perfilConsola = leer.nextLine().toLowerCase();
			if (perfilConsola.equalsIgnoreCase(Perfiles.coordinacion.name())) {
				perfil = Perfiles.coordinacion;
				perfilValido = true;

				do {
					System.out.println("Es senior? (s/n)");
					respuesta = leer.nextLine();
					verificar = Herramientas.confirmarRechazar(respuesta);
					if (!verificar) {
						System.out.println("\t Respuesta no valida, intentelo de nuevo");
					} else {
						if (respuesta.equalsIgnoreCase("s")) {

							String fecha;

							do {
								System.out.print("Introduzca desde que fecha(dd/mm/yyy):");
								fecha = leer.nextLine();
								verificar = Herramientas.verificarFecha(fecha);
								if (verificar == true) {

									fechaSenior = parseFecha(fecha);
								}

							} while (!verificar);

						}
					}
				} while (!verificar);

			} else if (perfilConsola.equalsIgnoreCase(Perfiles.artista.name())) {
				perfil = Perfiles.artista;
				perfilValido = true;
				do {
					System.out.println("Tiene apodo? (s/n)");
					respuesta = leer.nextLine();
					verificar = Herramientas.confirmarRechazar(respuesta);
					if (!verificar) {
						System.out.println("\\t Respuesta no valida, intentelo de nuevo");
					} else {
						if (respuesta.equalsIgnoreCase("s")) {
							System.out.println("Introduzca el apodo a continuacion por favor");
							apodo = leer.nextLine();
							if (apodo == null) {
								System.out.println("\t El apodo no puede estar vacio, intentelo de nuevo");
							}
						}
					}
				} while (!verificar);
				Credenciales credenciales = new Credenciales(nombreUsuario, password, perfil);
				Artista artista = new Artista(email, nombre, idNacionalidad, credenciales, apodo);

				// Especialidades
				int especialidadNum;
				boolean encontrado;
				System.out.println("--Especialidades--");
				for (int i = 0; i < Especialidades.values().length; i++) {
					System.out.println((i + 1) + "-" + Especialidades.values()[i]);
				}
				System.out.println("--Instrucciones--");
				System.out.println(
						"-Puede introducir cuantas especialidades desee (minimo una), además no se deberán repetir especialidades");
				System.out.println(
						"-Para añadir una especialidad introduzca uno de los valores asignados a cada especialidad ej: 1");
				System.out.println(
						"-Para finalizar la seleccion de especialidades simplemente introduzca un valor fuera del rango de especialidades.");
				do {
					encontrado = false;
					verificar = false;

					System.out.print("Introduzca la especialidad a continuacion: ");
					try {
						especialidadNum = leer.nextInt();
						leer.nextLine();
						if (especialidadNum > 0 && especialidadNum <= Especialidades.values().length) {
							especialidad = Especialidades.values()[especialidadNum - 1];
							for (Especialidades e : artista.getEspecialidad()) {
								if (especialidad == e) {
									System.out.println("\t Especialidad repetida, intentelo de nuevo por favor.");
									encontrado = true;
								}
							}
							if (encontrado == false) {

								artista.getEspecialidad().add(especialidad);
								System.out.println("\t Añadida especialidad: " + especialidad.toString());
							}
						} else {
							verificar = true;
							System.out.println("\t Seleccion de especialidades finalizada.");
						}
					} catch (InputMismatchException e) {
						System.out.println("Introduzca un número por favor");
						leer.nextLine();
						verificar = false;
					}
				} while (!verificar);

			} else {
				System.out.println("\\t Perfil no válido, intentelo de nuevo por favor");
			}
		} while (!perfilValido);
		// Verificar registro
		System.out.println("Estos son los datos que ha introducido:");
		System.out.println("Email: " + email);
		System.out.println("Nombre: " + nombre);
		System.out.println("Nacionalidad: " + nacionalidad);
		System.out.println("Nombre de usuario: " + nombreUsuario);

		do {
			System.out.println(
					"Desea continuar con el registro? Introduzca (s) para confirmar o (n) para cancelar el registro");
			respuesta = leer.nextLine();
			verificar = Herramientas.confirmarRechazar(respuesta);
			if (!verificar) {
				System.out.println("\t Respuesta no valida, intentelo de nuevo");
			}
		} while (!verificar);
		if (respuesta.equals("s")) {
			boolean repetido = false;
			repetido = buscarPersona(email, nombreUsuario);
			int id = generarId("persona");
			if (repetido == false) {
				try (BufferedWriter bw = new BufferedWriter(
						new FileWriter(PropertiesClass.obtenerPropiedad("credenciales"), true))) {
					String credencial = id + "|" + nombreUsuario + "|" + password + "|" + email + "|" + nombre + "|"
							+ nacionalidad + "|" + perfil.toString() + "\n";
					bw.write(credencial);
					System.out.println("Persona registrada con exito");

				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}

		} else {
			System.out.println("Registro interrumpido");
		}
	}

	/**
	 * Metodo iniciar sesion
	 * 
	 * @param nombreUsuario
	 * @param password
	 * @return
	 */
	public static Perfiles iniciarSesion(String nombreUsuario, String password) {
		String linea;
		String[] datos;
		String perfilUsuario = "";
		Perfiles perfil = null;
		try (BufferedReader br = new BufferedReader(new FileReader(PropertiesClass.obtenerPropiedad("credenciales")))) {

			while ((linea = br.readLine()) != null) {
				datos = linea.split("\\|");
				if (nombreUsuario.equals(datos[1]) && password.equals(datos[2])) {
					System.out.println("\t Sesion iniciada con exito");
					System.out.println("Bienvenido a " + datos[6] + " " + nombreUsuario + ", que desea hacer?");
					perfilUsuario = datos[6];
					perfil = Perfiles.valueOf(perfilUsuario);

				}

			}
			if (nombreUsuario.equals(PropertiesClass.obtenerPropiedad("usuarioAdmin"))
					&& password.equals(PropertiesClass.obtenerPropiedad("contraseñaAdmin"))) {
				perfil = Perfiles.admin;
			}
		} catch (FileNotFoundException e) {
			System.out.println("Fichero no encontrado" + e.getMessage());
		} catch (IOException e) {
		}

		return perfil;
	}

	/**
	 * Metodo para comprobar si el id del espectáculo ya existe
	 * 
	 * @param id
	 * @return boolean
	 */
	public static boolean comprobarIdEspectaculo(Long id) {

		boolean encontrado = false;
		try (ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream(PropertiesClass.obtenerPropiedad("espectaculos")))) {

			while (true) {
				try {
					Espectaculo espectaculo = (Espectaculo) ois.readObject();
					if (espectaculo.getId().equals(id)) {
						encontrado = true;
					}
				} catch (EOFException e) {
					break;
				}
			}
		} catch (ClassNotFoundException | IOException e) {
		}

		return encontrado;
	}

	/**
	 * Metodo para comprobar si el nombre de espectaculo es unico
	 * 
	 * @param id
	 * @return boolean
	 */
	public static boolean comprobarNombreEspectaculo(String nombre) {

		boolean encontrado = false;
		try (ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream(PropertiesClass.obtenerPropiedad("espectaculos")))) {

			while (true) {
				try {
					Espectaculo espectaculo = (Espectaculo) ois.readObject();
					if (espectaculo.getNombre().equalsIgnoreCase(nombre)) {
						encontrado = true;
					}
				} catch (EOFException e) {
					break;
				}
			}
		} catch (ClassNotFoundException | IOException e) {
			System.out.println();
		}
		return encontrado;
	}

	/**
	 * Metodo para generar coordinadores
	 * 
	 * @return Map<Integer,String>
	 */
	public static Map<Long, String> generarCoordinadores() {

		Map<Long, String> coordinadores = new LinkedHashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader(PropertiesClass.obtenerPropiedad("credenciales")))) {
			String linea;
			String[] datos;
			Long contador = 1L;
			while ((linea = br.readLine()) != null) {
				datos = linea.split("\\|");
				coordinadores.put(contador, datos[1]);
				contador++;

			}
		} catch (FileNotFoundException e) {
			System.out.println("Fichero no encontrado");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return coordinadores;
	}

	/**
	 * Método para escribir espectaculo en fichero binario Borrara y escribirá uno
	 * nuevo actualizado para evitar duplicidad de cabeceras
	 * 
	 * @param espectaculo
	 */
	public static void escribirEspectaculo(Espectaculo espectaculo) {
		File file = new File(PropertiesClass.obtenerPropiedad("espectaculos"));
		List<Espectaculo> lista = new ArrayList<>();

		if (file.exists() && file.length() > 0) {
			try (ObjectInputStream ois = new ObjectInputStream(
					new FileInputStream(PropertiesClass.obtenerPropiedad("espectaculos")))) {
				while (true) {
					try {
						Espectaculo e = (Espectaculo) ois.readObject();
						lista.add(e);
					} catch (EOFException ex) {
						break;
					}
				}
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("Error leyendo archivo: " + e.getMessage());
			}
		}
		lista.add(espectaculo);

		try (ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(PropertiesClass.obtenerPropiedad("espectaculos")))) {
			for (Espectaculo e : lista) {
				oos.writeObject(e);
			}
		} catch (IOException e) {
			System.out.println("Error al escribir archivo: " + e.getMessage());
		}
	}

	/**
	 * Metodo para pedir datos de espectaulo
	 * 
	 * @param perfil
	 */
	public static void crearEspectaculo(String perfil, String nombreUsuario) {
		String nombre;
		boolean verificar = false;
		Long id = 0L;
		do {
			try {
				System.out.print("Introduzca el id a continuacion: ");
				id = leer.nextLong();
				leer.nextLine();
				if (comprobarIdEspectaculo(id) == false) {
					verificar = true;

				} else {
					System.out.println("\t Este id ya esta registrado, intentelo de nuevo");
					verificar = false;
				}
			} catch (InputMismatchException e) {
				System.out.println("\t id no valido, intentelo de nuevo");
				leer.nextLine();

			}

		} while (!verificar);
		do {
			System.out.print("Introduzca el nombre del espectaculo continuacion:");
			nombre = leer.nextLine();
			if (nombre.length() > 0 && nombre.length() <= 25) {
				if (comprobarNombreEspectaculo(nombre) == false) {
					verificar = true;

				} else {
					System.out.println("\t Este nombre ya esta registrado, intentelo de nuevo");
					verificar = false;
				}
			} else {
				verificar = false;
				System.out.println("\t Lo siento solo se admiten nombres de 1 a 25 caracteres");
			}

		} while (!verificar);

		String fechaIni;
		String fechaFin;
		LocalDate fechaInicio = null;
		LocalDate fechaFinal = null;
		do {

			do {
				System.out.print("Introduzca la fecha de inicio(dd/mm/yyy):");
				fechaIni = leer.nextLine();
				verificar = Herramientas.verificarFecha(fechaIni);
				if (verificar == true) {

					fechaInicio = parseFecha(fechaIni);
				}

			} while (!verificar);

			do {
				System.out.print("Introduzca la fecha de finalizacion(dd/mm/yyy):");
				fechaFin = leer.nextLine();
				verificar = Herramientas.verificarFecha(fechaFin);
				if (verificar == true) {

					fechaFinal = parseFecha(fechaFin);
				}

			} while (!verificar);

			// Sacamos la diferencia entre fechas
			Period diferencia = Period.between(fechaInicio, fechaFinal);
			if (diferencia.getYears() > 1 || diferencia.getYears() < 0) {
				verificar = false;
				System.out.println(
						"\t Error la diferencia dentre la fecha de inicio y la final no puede superar el año, ni ser negativa");
			} else
				verificar = true;
		} while (!verificar);

		Espectaculo espectaculo = new Espectaculo(id, nombre, fechaInicio, fechaFinal);

		if (perfil.equalsIgnoreCase(Perfiles.coordinacion.toString())) {

			Long contador = 1L;
			try (BufferedReader br = new BufferedReader(
					new FileReader(PropertiesClass.obtenerPropiedad("credenciales")))) {
				String linea;
				String[] datos;
				String nombreU = "";
				do {
					while ((linea = br.readLine()) != null) {
						datos = linea.split("\\|");
						nombreU = datos[6];
						if (datos[6].equals(perfil)) {
							contador++;
						}
					}
				} while (nombreU.equalsIgnoreCase(nombreUsuario));

			} catch (FileNotFoundException e) {
				System.out.println("Fichero no encontrado");
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}

			espectaculo.setIdCoord(contador);
			escribirEspectaculo(espectaculo);
			System.out.println("\t Espectaculo registrado con exito");
		} else if (perfil == Perfiles.admin.toString()) {
			Long coordinador = 0L;
			Map<Long, String> coordinadores = generarCoordinadores();
			verificar = false;
			do {
				try {
					System.out.println("--Coordinadores--");
					for (Map.Entry<Long, String> entry : coordinadores.entrySet()) {

						System.out.println("-" + entry.getKey() + ": " + entry.getValue());

					}
					System.out.println("Asigne uno de los coordinadores al espectaculo a continuacion:");

					coordinador = leer.nextLong();
					leer.nextLine();

					if (coordinadores.containsKey(coordinador)) {

						espectaculo.setIdCoord(coordinador);
						System.out.println(
								"\t Coordinador: " + coordinadores.get(coordinador) + " añadido correctamente");
						escribirEspectaculo(espectaculo);
						System.out.println("\t Espectaculo registrado con exito.");
						verificar = true;
					}
				} catch (InputMismatchException e) {
					System.out.println("\t Error opcion no valida, intentelo de nuevo");
					leer.nextLine();
				}

			} while (!verificar);

		}
	}

	/**
	 * Menu para crear o modificar espectaculos
	 * 
	 * @param perfil
	 */
	public static void crearModificarEspectaculos(String perfil, String nombreUsuario) {
		int eleccion = 0;
		do {
			try {
				System.out.println("1-Crear nuevo espectaculo");
				System.out.println("2-Modificar espectaculo existente");
				System.out.println("3-Salir");
				eleccion = leer.nextInt();
				leer.nextLine();
				switch (eleccion) {
				case 1:
					crearEspectaculo(perfil, nombreUsuario);
					break;
				case 2:
					System.out.println(
							"\t Funcionalidad no disponible,se implementará en las proximas actualizaciones.Disculpe las molestias.");
					break;
				case 3:
					break;
				default:
					System.out.println("\t Opcion no valida, intentelo de nuevo");
				}
			} catch (InputMismatchException e) {
				System.out.println("\t Opcion no valida, intentelo de nuevo");
				leer.nextLine();
			}

		} while (eleccion != 3);
	}

	/**
	 * Método para gestionar epesctaculos
	 * 
	 * @param perfil
	 */
	public static void gestionarEspectaculos(String perfil, String nombreUsuario) {

		System.out.println("--Gestion de espectaculos--");
		int eleccion = 0;
		do {
			try {
				System.out.println("1-Crear o modificar espectaculo");
				System.out.println("2-Crear o modificar número");
				System.out.println("3-Asignar artistas");
				System.out.println("4-Salir de la gestion de espectaculos");
				eleccion = leer.nextInt();
				leer.nextLine();
				switch (eleccion) {
				case 1:
					crearModificarEspectaculos(perfil, nombreUsuario);
					break;
				case 2:
					System.out.println(
							"\t Funcionalidad no disponible,se implementará en las proximas actualizaciones.Disculpe las molestias.");
					break;
				case 3:
					System.out.println(
							"\t Funcionalidad no disponible,se implementará en las proximas actualizaciones.Disculpe las molestias.");
					break;
				case 4:
					System.out.println("\t Gestion de espectaculos finalizada.");
					break;
				default:
					System.out.println("Opcion no valida,intentelo de nuevo");
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("Opcion no valida, intentelo de nuevo");
				leer.nextLine();
			}
		} while (eleccion != 4);
	}

	/**
	 * Metodo para mostrar espectaculos
	 */
	public static void mostrarEspectaculos() {

		try (ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream(PropertiesClass.obtenerPropiedad("espectaculos")))) {
			System.out.println();
			System.out.printf("| %-5s | %-25s | %-15s | %-15s%n", "ID", "Nombre", "Fecha Inicio", "Fecha Final");
			System.out.println("--------------------------------------------------------------------1");
			while (true) {

				try {

					Espectaculo espectaculo = (Espectaculo) ois.readObject();
					System.out.printf("| %-5d | %-25s | %-15s | %-15s%n", espectaculo.getId(), espectaculo.getNombre(),
							espectaculo.getFechaini(), espectaculo.getFechafin());

				} catch (EOFException e) {
					break;
				}
			}
			System.out.println();
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}

		catch (IOException e) {
			System.out.println("Error al escribir el archivo" + e.getMessage());
		}

	}

	/**
	 * Metodo main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// registrarPersona();
		System.out.println("\n*** 🎪 ¡BIENVENIDOS AL GRAN CIRCO! 🎪 ***");
		int eleccion = 0;
		String usuario;
		String password;
		boolean encontrado;
		Perfiles perfil;
		Sesion sesion = new Sesion();
		do {
			try {
				System.out.println("--Perfil invitado");
				System.out.println("1-Visualizar espectaculos");
				System.out.println("2-Log in");
				System.out.print("Introduzca una opcion a continuacion: ");
				eleccion = leer.nextInt();
				leer.nextLine();

				switch (eleccion) {

				case 1:
					mostrarEspectaculos();
					break;

				case 2:
					System.out.print("Introduzca el nombre de usuario a continuacion: ");
					usuario = leer.nextLine();
					System.out.print("Introduzca la contraseña a continuacion: ");
					password = leer.nextLine();
					encontrado = buscarPersona(password, usuario);
					if (encontrado) {
						perfil = iniciarSesion(usuario, password);

						switch (perfil) {

						case coordinacion:
							sesion.setNombre(usuario);
							sesion.setPerfil(Perfiles.coordinacion);
							do {
								try {

									System.out.println("1-Ver espectaculo completo");
									System.out.println("2-Gestionar espectaculos");
									System.out.println("3-Log out");
									eleccion = leer.nextInt();
									switch (eleccion) {
									case 1:
										System.out.println(
												"\t Funcionalidad no disponible,se implementará en las proximas actualizaciones.Disculpe las molestias.");
										break;
									case 2:
										gestionarEspectaculos(perfil.toString(), usuario);
										break;
									case 3:
										sesion = new Sesion();
										System.out.println("\t Logged out");
										break;
									default:
										System.out.println("Opcion no valida, intentelo de nuevo");
									}

								} catch (InputMismatchException e) {
									System.out.println("\t Opcion no válida, intentelo de nuevo");
									leer.nextLine();
								}
							} while (eleccion != 3);
							eleccion = 0;
							continue;

						case artista:
							sesion.setNombre(usuario);
							sesion.setPerfil(Perfiles.artista);
							do {
								try {
									System.out.println("1-Ver ficha");
									System.out.println("2-Ver espectaculo completo");
									System.out.println("3-Log out");
									eleccion = leer.nextInt();
									switch (eleccion) {
									case 1:
										System.out.println(
												"\t Funcionalidad no disponible,se implementará en las proximas actualizaciones.Disculpe las molestias.");
										break;
									case 2:
										System.out.println(
												"\t Funcionalidad no disponible,se implementará en las proximas actualizaciones.Disculpe las molestias.");
										break;
									case 3:
										sesion = new Sesion();
										System.out.println("\t Logged out");
										break;
									default:
										System.out.println("\t Opcion no válida, intentalo de nuevo");
										break;
									}
								} catch (InputMismatchException e) {
									System.out.println("\t Opcion no válida, intentelo de nuevo");
									leer.nextLine();
								}

							} while (eleccion != 3);
							eleccion = 0;
							continue;
						case admin:

							sesion.setNombre(PropertiesClass.obtenerPropiedad("usuarioAdmin").toString());
							System.out.println("---ADMINISTRADOR---");
							do {
								try {
									System.out.println("1-Registrar persona");
									System.out.println("2-Gestionar espectaculos");
									System.out.println("3-Log out");
									eleccion = leer.nextInt();
									leer.nextLine();

									switch (eleccion) {
									case 1:
										registrarPersona();
										break;
									case 2:
										gestionarEspectaculos(PropertiesClass.obtenerPropiedad("usuarioAdmin"),
												PropertiesClass.obtenerPropiedad("usuarioAdmin"));
										break;
									case 3:
										sesion = new Sesion();
										System.out.println("\t Logged out");
										break;
									default:
										System.out.println("Opcion no valida,intentelo de nuevo");

									}
								} catch (InputMismatchException e) {
									System.out.println("Opcion no valida,intentelo de nuevo");
									leer.nextLine();
								}
							} while (eleccion != 3);
							eleccion = 0;
							continue;
						}
					} else {
						System.out.println("\t Perfil no encontrado, intentelo de nuevo");
						continue;
					}
				default:
					System.out.println("\t Opcion no valida,intentelo de nuevo.");
					break;

				}
			} catch (InputMismatchException e) {
				System.out.println("\t Opcion no válida, intentelo de nuevo");
				leer.nextLine();
			}
		} while (eleccion != 3);

	}
}
