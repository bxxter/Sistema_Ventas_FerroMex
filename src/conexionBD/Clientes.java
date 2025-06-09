/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexionBD;

/**
 *
 * @author azael
 */
public class Clientes {
    private int id;
    private String nombre;
    private String apellido;
    private String correo;

    // Constructor para insertar sin id
    public Clientes(String nombre, String apellido, String correo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
    }
    
    public Clientes(){};

    // Constructor con id
    public Clientes(int id, String nombre, String apellido, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
    }

    // Getters y setters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getCorreo() { return correo; }

    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setCorreo(String correo) { this.correo = correo; }
}
