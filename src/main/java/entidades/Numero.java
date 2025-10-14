/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Adrian Pañeda Hamadi
 */
public class Numero {
    
    private Long id;
    private int orden;
    private String nombre;
    private double duracion;
    private Long idEspectaculo;
    private Set<Artista> artistas = new HashSet<>();

    public Numero(Long id, int orden, String nombre, double duracion, Long idEspectaculo) {
        this.id = id;
        this.orden = orden;
        this.nombre = nombre;
        this.duracion = duracion;
        this.idEspectaculo = idEspectaculo;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getDuracion() {
		return duracion;
	}

	public void setDuracion(double duracion) {
		this.duracion = duracion;
	}

	public Long getIdEspectaculo() {
		return idEspectaculo;
	}

	public void setIdEspectaculo(Long idEspectaculo) {
		this.idEspectaculo = idEspectaculo;
	}

	public Set<Artista> getArtistas() {
		return artistas;
	}

	public void setArtistas(Set<Artista> artistas) {
		this.artistas = artistas;
	}
 
}
