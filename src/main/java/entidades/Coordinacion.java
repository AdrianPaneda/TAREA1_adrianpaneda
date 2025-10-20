/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author Adrian Pañeda Hamadi
 */
public class Coordinacion extends Persona {
    
    private Long idCoord;
    private boolean senior = false;
    private LocalDate fechasenior = null;
    private Set<Espectaculo> espectaculos = new LinkedHashSet<>();

    public Coordinacion(String email, String nombre, String nacionalidad, Credenciales credenciales,
			boolean senior, LocalDate fechasenior) {
		super(email, nombre, nacionalidad, credenciales);
		this.senior = senior;
		this.fechasenior = fechasenior;
		
	}

	public Long getIdCoord() {
        return idCoord;
    }

    public void setIdCoord(Long idCoord) {
        this.idCoord = idCoord;
    }

    public boolean isSenior() {
        return senior;
    }

    public void setSenior(boolean senior) {
        this.senior = senior;
    }

    public LocalDate getFechasenior() {
        return fechasenior;
    }

    public void setFechasenior(LocalDate fechasenior) {
        this.fechasenior = fechasenior;
    }

    public Set<Espectaculo> getEspectaculos() {
        return espectaculos;
    }

    public void setEspectaculos(Set<Espectaculo> espectaculos) {
        this.espectaculos = espectaculos;
    }
   
}
