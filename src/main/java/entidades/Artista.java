/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.HashSet;
import java.util.Set;

/**
 * Clase Artista
 * @author Adrian Pañeda Hamadi
 */
public class Artista extends Persona {
    
    private Long idArt;
    private String apodo = null;
    private Set<Especialidades> especialidades = new HashSet<>();
    private Set<Numero> numeros = new HashSet<>();

    

    public Artista(String email, String nombre, String nacionalidad, Credenciales credenciales,
			String apodo) {
		super(email, nombre, nacionalidad, credenciales);
		this.apodo = apodo;
	}

	public Long getIdArt() {
        return idArt;
    }

    public void setIdArt(Long idArt) {
        this.idArt = idArt;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public Set<Especialidades> getEspecialidad() {
        return especialidades;
    }

    public void setEspecialidad(Set<Especialidades> especialidad) {
        this.especialidades = especialidad;
    }

    public Set<Numero> getNumeros() {
        return numeros;
    }

    public void setNumeros(Set<Numero> numeros) {
        this.numeros = numeros;
    }
    
    
    
    
    
    
}
