/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexionBD;

public class Categoria {
    private int id;
    private String nombre;

    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    
    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    // Getters y setters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
