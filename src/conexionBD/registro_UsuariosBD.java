/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexionBD;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class registro_UsuariosBD {
    public static boolean registrarUsuario(String nombre, String correo, String contrasena, int rolId) {
        try (Connection conn = Conexion.getConnection()) {

            // Encriptar la contrasena
            String hashContrasena = BCrypt.withDefaults().hashToString(12, contrasena.toCharArray());
            
            String sql = "INSERT INTO usuarios (nombre, correo, contrasena, rol_id) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre);
            stmt.setString(2, correo);
            stmt.setString(3, hashContrasena);
            stmt.setInt(4, rolId);

            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    
    public static Usuario obtenerUsuarioAutenticado(String correo, String contrasena) {
        try (Connection conn = Conexion.getConnection()) {
            String sql = "SELECT nombre, correo, contrasena, rol_id FROM usuarios WHERE correo = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String hashGuardado = rs.getString("contrasena");
                BCrypt.Result resultado = BCrypt.verifyer().verify(contrasena.toCharArray(), hashGuardado);

                if (resultado.verified) {
                    String nombre = rs.getString("nombre");
                    String correoDB = rs.getString("correo");
                    String rol = obtenerNombreRol(rs.getInt("rol_id"), conn);

                    return new Usuario(nombre, correoDB, hashGuardado, rol, correoDB);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    
    private static String obtenerNombreRol(int rolId, Connection conn) {
        String sql = "SELECT nombre FROM roles WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, rolId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nombre");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "desconocido";
    }
    
    public static boolean correoExiste(String correo) {
        try (Connection conn = Conexion.getConnection()) {
            String sql = "SELECT COUNT(*) FROM usuarios WHERE correo = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // true si ya existe
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean actualizarUsuario(int id, String nombre, String correo, String contrasena, int rolId) {
    String sql;
    boolean actualizarPassword = contrasena != null && !contrasena.isEmpty();

    if (actualizarPassword) {
        sql = "UPDATE usuarios SET nombre = ?, correo = ?, contrasena = ?, rol_id = ? WHERE id = ?";
    } else {
        sql = "UPDATE usuarios SET nombre = ?, correo = ?, rol_id = ? WHERE id = ?";
    }

    try (Connection conn = Conexion.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, nombre);
        stmt.setString(2, correo);
        
        if (actualizarPassword) {
            String hashContrasena = BCrypt.withDefaults().hashToString(12, contrasena.toCharArray());
            stmt.setString(3, hashContrasena);
            stmt.setInt(4, rolId);
            stmt.setInt(5, id);
        } else {
            stmt.setInt(3, rolId);
            stmt.setInt(4, id);
        }

        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

}