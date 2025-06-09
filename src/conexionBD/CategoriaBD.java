/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaBD {

    public List<Categoria> obtenerCategorias() {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT id, nombre FROM categorias";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Categoria c = new Categoria(
                    rs.getInt("id"),
                    rs.getString("nombre")
                );
                lista.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    
    public boolean guardarCategoria(Categoria categoria) {
        String sql = "INSERT INTO categorias (nombre) VALUES (?)";
        try (Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, categoria.getNombre());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarCategoria(Categoria categoria) {
        String sql = "UPDATE categorias SET nombre = ? WHERE id = ?";
        try (Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, categoria.getNombre());
            ps.setInt(2, categoria.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public boolean eliminarCategoria(int id) {
        String sql = "DELETE FROM categorias WHERE id = ?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Categoria> buscarCategoriasPorNombre(String texto) {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT id, nombre FROM categorias WHERE nombre LIKE ?";

        try (Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + texto + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Categoria categoria = new Categoria(
                    rs.getInt("id"),
                    rs.getString("nombre")
                );
                categorias.add(categoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categorias;
    }

}
