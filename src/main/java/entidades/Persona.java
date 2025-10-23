/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 * Clase Persona
 * @author Adrian Pañeda Hamadi
 */
public abstract class Persona {
    
    private Long id;
    private String email;
    private String nombre;
    private String nacionalidad;
    private Credenciales credenciales;

    public Persona(String email, String nombre, String nacionalidad, Credenciales credenciales) {
        this.id = credenciales.getId();
        this.email = email;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.credenciales = credenciales;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Credenciales getCredenciales() {
        return credenciales;
    }

    public void setCredenciales(Credenciales credenciales) {
        this.credenciales = credenciales;
    }
    
    
    
    
    
}
