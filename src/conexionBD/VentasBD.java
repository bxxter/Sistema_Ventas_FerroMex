/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexionBD;


import clases.ItemCarrito;
import java.sql.*;
import java.util.List;

public class VentasBD {
    
    private int guardarVenta(int idCliente) throws SQLException {
    // Inserta en la tabla ventas la venta y retorna el ID generado
    String sql = "INSERT INTO ventas (id_cliente, fecha) VALUES (?, NOW())";
    try (Connection con = Conexion.getConnection();
         PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
         
        ps.setInt(1, idCliente);
        ps.executeUpdate();
        
        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
    }
    throw new SQLException("No se pudo obtener el ID de la venta");
}

private void guardarDetalleVenta(int idVenta, ItemCarrito item) throws SQLException {
    String sql = "INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio) VALUES (?, ?, ?, ?)";
    try (Connection con = Conexion.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
         
        ps.setInt(1, idVenta);
        ps.setInt(2, item.getProducto().getId());
        ps.setInt(3, item.getCantidad());
        ps.setDouble(4, item.getProducto().getPrecio());
        ps.executeUpdate();
    }
}

private void actualizarStockProducto(int idProducto, int cantidadVendida) throws SQLException {
    String sql = "UPDATE productos SET stock = stock - ? WHERE id = ? AND stock >= ?";
    try (Connection con = Conexion.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
         
        ps.setInt(1, cantidadVendida);
        ps.setInt(2, idProducto);
        ps.setInt(3, cantidadVendida);
        int updated = ps.executeUpdate();
        
        if (updated == 0) {
            throw new SQLException("No hay stock suficiente para el producto ID " + idProducto);
        }
    }
}

public int realizarVenta(int idCliente, List<ItemCarrito> carrito) throws SQLException {
        int idVenta = -1;
        try (Connection con = Conexion.getConnection()) {
            try {
                con.setAutoCommit(false);

                // Guardar la venta principal
                String sqlVenta = "INSERT INTO ventas (id_cliente, fecha) VALUES (?, NOW())";
                try (var psVenta = con.prepareStatement(sqlVenta, java.sql.Statement.RETURN_GENERATED_KEYS)) {
                    psVenta.setInt(1, idCliente);
                    psVenta.executeUpdate();
                    try (var rs = psVenta.getGeneratedKeys()) {
                        if (rs.next()) {
                            idVenta = rs.getInt(1);
                        } else {
                            throw new SQLException("No se pudo obtener el ID de la venta");
                        }
                    }
                }

                // Guardar detalles y actualizar stock
                String sqlDetalle = "INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio) VALUES (?, ?, ?, ?)";
                String sqlActualizarStock = "UPDATE productos SET stock = stock - ? WHERE id = ? AND stock >= ?";

                try (var psDetalle = con.prepareStatement(sqlDetalle);
                     var psStock = con.prepareStatement(sqlActualizarStock)) {

                    for (ItemCarrito item : carrito) {
                        // Insertar detalle
                        psDetalle.setInt(1, idVenta);
                        psDetalle.setInt(2, item.getProducto().getId());
                        psDetalle.setInt(3, item.getCantidad());
                        psDetalle.setDouble(4, item.getProducto().getPrecio());
                        psDetalle.executeUpdate();

                        // Actualizar stock
                        psStock.setInt(1, item.getCantidad());
                        psStock.setInt(2, item.getProducto().getId());
                        psStock.setInt(3, item.getCantidad());

                        int rowsAffected = psStock.executeUpdate();
                        if (rowsAffected == 0) {
                            throw new SQLException("No hay stock suficiente para el producto ID " + item.getProducto().getId());
                        }
                    }
                }

                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        }
        return idVenta;
    }


}
