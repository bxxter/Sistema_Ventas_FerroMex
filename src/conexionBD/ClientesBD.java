/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ClientesBD {
        public boolean existeClientePorCorreo(String correo) {
        String sql = "SELECT COUNT(*) FROM clientes WHERE correo = ?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int obtenerIdClientePorCorreo(String correo) {
        String sql = "SELECT id FROM clientes WHERE correo = ?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // No encontrado
    }

    public boolean guardarCliente(Clientes cliente) {
        String sql = "INSERT INTO clientes (nombre, apellido, correo) VALUES (?, ?, ?)";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getCorreo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Clientes> obtenerClientes() {
        List<Clientes> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, apellido, correo FROM clientes WHERE activo = TRUE";

        try (Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Clientes c = new Clientes(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("correo")
                );
                lista.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }


    public List<Clientes> buscarClientes(String texto) {
        List<Clientes> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, apellido, correo FROM clientes " +
                    "WHERE activo = TRUE AND (nombre LIKE ? OR apellido LIKE ?)";

        try (Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + texto + "%");
            ps.setString(2, "%" + texto + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Clientes c = new Clientes(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("correo")
                );
                lista.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    public boolean eliminarCliente(int id) {
        String sql = "UPDATE clientes SET activo = FALSE WHERE id = ?";
        try (Connection con = Conexion.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean insertarCliente(Clientes cliente) {
        String sql = "INSERT INTO clientes (nombre, apellido, correo) VALUES (?, ?, ?)";
        try (Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getCorreo());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean actualizarCliente(Clientes cliente) {
        String sql = "UPDATE clientes SET nombre = ?, apellido = ?, correo = ? WHERE id = ?";
        try (Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getCorreo());
            ps.setInt(4, cliente.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
   
public Clientes obtenerClientePorId(int idCliente) {
    Clientes cliente = null;
    String sql = "SELECT id, nombre, apellido, correo FROM clientes WHERE id = ?";
    try (Connection con = Conexion.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setInt(1, idCliente);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                cliente = new Clientes();
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setCorreo(rs.getString("correo"));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return cliente;
}



}
