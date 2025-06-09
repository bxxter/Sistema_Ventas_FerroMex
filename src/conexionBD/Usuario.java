package conexionBD;

public class Usuario {
    private String nombre;
    private String user;
    private String password;
    private String rol;
    private String email;
    private int id;
    private int rolId;

    public Usuario(String nombre, String usuario, String contrasena, String rol, String email){
        this.nombre = nombre;
        this.user = usuario;
        this.password = contrasena;
        this.rol = rol;
        this.email = email;
    }
    
    // Constructor para mostrar en la tabla, sin contraseña
    public Usuario(int id, String nombre, String email, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
    }
    
    
    
    public Usuario(int id, String nombre, String usuario, String contrasena, String rol, String email) {
        this.id = id;
        this.nombre = nombre;
        this.user = usuario;
        this.password = contrasena;
        this.rol = rol;
        this.email = email;
    }


    // Setters y Getters
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return nombre;
    }

    public void setUser(String user){
        this.user = user;
    }

    public String getUser(){
        return user;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
    
    public int getId() {
        return id;
    }
    
    public int getRolId() { 
        return rolId; 
    }
}
