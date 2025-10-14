/**
* Clase Sesion.java
*
* @author Adrian Pañeda Hamadi
* @version 1.0
*/
package entidades;

public class Sesion {
	
	private String nombre;
	private Perfiles perfil;
	
	public Sesion() {
		this.nombre = "Invitado";
		this.perfil = null;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Perfiles getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfiles perfil) {
		this.perfil = perfil;
	}
}
