/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexionBD;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author azael
 */
public class UsuarioBD {
    public List<Usuario> obtenerTodos() {
        List<Usuario> lista = new ArrayList<>();

        String sql = "SELECT u.id, u.nombre, u.correo, r.nombre AS rol " +
                    "FROM usuarios u JOIN roles r ON u.rol_id = r.id";

        try (Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("rol")
                );
                lista.add(usuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public List<Usuario> buscarUsuariosPorNombre(String texto) {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id, nombre, correo, rol_id FROM usuarios WHERE nombre LIKE ?";

        try (Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + texto + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("rol_id")
                );
                usuarios.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }
    
    


}
