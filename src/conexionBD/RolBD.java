/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RolBD {

    public List<Rol> obtenerRoles() {
        List<Rol> roles = new ArrayList<>();
        try (Connection conn = Conexion.getConnection()) {
            String sql = "SELECT id, nombre FROM roles";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                roles.add(new Rol(id, nombre));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return roles;
    }
}
