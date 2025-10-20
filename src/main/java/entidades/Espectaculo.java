/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;
import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author Adrian Pañeda Hamadi
 */
public class Espectaculo implements Serializable {
    
    private Long id;
    private String nombre;
    private LocalDate fechaini;
    private LocalDate fechafin;
    private Numero[] numeros = new Numero[3];
    private Long idCoord;


	public Espectaculo(String nombre, LocalDate fechaini, LocalDate fechafin) {
        
        this.nombre = nombre;
        this.fechaini = fechaini;
        this.fechafin = fechafin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaini() {
        return fechaini;
    }

    public void setFechaini(LocalDate fechaini) {
        this.fechaini = fechaini;
    }

    public LocalDate getFechafin() {
        return fechafin;
    }

    public void setFechafin(LocalDate fechafin) {
        this.fechafin = fechafin;
    }

    public Numero[] getNumeros() {
        return numeros;
    }

    public void setNumeros(Numero[] numeros) {
        this.numeros = numeros;
    }

	public Long getIdCoord() {
		return idCoord;
	}

	public void setIdCoord(Long idCoord) {
		this.idCoord = idCoord;
	}
    
    
    
    
}
