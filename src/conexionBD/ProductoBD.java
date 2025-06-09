/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoBD {

    public List<Producto> obtenerProductos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, precio, stock, categoria FROM productos WHERE activo = TRUE";

        try (Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getDouble("precio"),
                    rs.getInt("stock"),
                    rs.getString("categoria")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    
    
    public boolean existeProductoConId(int id) {
        String sql = "SELECT COUNT(*) FROM productos WHERE id = ?";
        try (Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean guardarProducto(Producto p) {
        String sql = "INSERT INTO productos (nombre, precio, stock, categoria) VALUES (?, ?, ?, ?)";

        try (Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecio());
            ps.setInt(3, p.getStock());
            ps.setString(4, p.getCategoria());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<String> buscarProductosCoincidenciasBD(String texto) {
        List<String> resultados = new ArrayList<>();
        String sql = "SELECT id, nombre FROM producto WHERE id LIKE ? OR nombre LIKE ? LIMIT 10";

        try (Connection conn = Conexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
        
            String query = "%" + texto + "%";
            stmt.setString(1, query);
            stmt.setString(2, query);
            ResultSet rs = stmt.executeQuery();
        
            while (rs.next()) {
                String resultado = rs.getString("id") + " - " + rs.getString("nombre");
                resultados.add(resultado);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultados;
    }
    
    public boolean actualizarProducto(Producto producto) {
        String sql = "UPDATE productos SET nombre = ?, precio = ?, stock = ?, categoria = ? WHERE id = ?";
        try (Connection conn = Conexion.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, producto.getNombre());
            stmt.setDouble(2, producto.getPrecio());
            stmt.setInt(3, producto.getStock());
            stmt.setString(4, producto.getCategoria());
            stmt.setInt(5, producto.getId());

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean eliminarProducto(int id) {
        String sql = "UPDATE productos SET activo = FALSE WHERE id = ?";
        try (Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

   public Producto obtenerProductoPorId(int id) {
    String sql = "SELECT id, nombre, precio, stock, categoria FROM productos WHERE id = ?";
    try (Connection con = Conexion.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Producto(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getDouble("precio"),
                rs.getInt("stock"),
                rs.getString("categoria")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

    public List<Producto> buscarProductosPorNombre(String texto) {
        List<Producto> productos = new ArrayList<>();
    
        // Solo traer productos que estén activos
        String sql = "SELECT id, nombre, precio, stock, categoria FROM productos WHERE nombre LIKE ? AND activo = TRUE";
        
        try (Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
        
            ps.setString(1, "%" + texto + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = new Producto(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getDouble("precio"),
                    rs.getInt("stock"),
                    rs.getString("categoria")
                );
                productos.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }


    public boolean descontarStock(int idProducto, int cantidadVendida) {
        String sql = "UPDATE productos SET stock = stock - ? WHERE id = ? AND stock >= ?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cantidadVendida);
            ps.setInt(2, idProducto);
            ps.setInt(3, cantidadVendida);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
