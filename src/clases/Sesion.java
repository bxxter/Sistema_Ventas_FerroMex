package clases;

import conexionBD.Usuario;

public class Sesion {
    private static Usuario usuarioActual;

    public static void iniciarSesion(Usuario usuario) {
        usuarioActual = usuario;
    }

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static void cerrarSesion() {
        usuarioActual = null;
    }
    
     public static int obtenerRolId() {
        if (usuarioActual != null) {
            String rol = usuarioActual.getRol();
            switch (rol.toLowerCase()) {
                case "admin":
                case "administrador":
                    return 1;
                case "cajero":
                    return 2;
                case "supervisor":
                    return 3;
                default:
                    return -1;
            }
        }
        return -1;
    }

    // Métodos para checar el tipo de usuario
    public static boolean esAdmin() {
        return obtenerRolId() == 1;
    }

    public static boolean esCajero() {
        return obtenerRolId() == 2;
    }

    public static boolean esSupervisor() {
        return obtenerRolId() == 3;
    }
}
