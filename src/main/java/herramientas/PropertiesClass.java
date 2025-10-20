package herramientas;

import java.io.FileInputStream;
import java.util.Properties;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PropertiesClass {
	
	private static Properties propiedades = new Properties();

	static {
	try(FileInputStream in = new FileInputStream("src/main/resources/application.properties")) {
		propiedades.load(in);
	}catch(FileNotFoundException e) {
		System.out.println("Error, application.properties no entrado");
	}catch(IOException e) {System.out.println(e.getLocalizedMessage());}
	}
	public static String obtenerPropiedad(String valor) {
	return propiedades.getProperty(valor).toString();	
	}
}

