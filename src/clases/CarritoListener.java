/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import conexionBD.Producto;

public interface CarritoListener {
    void agregarProducto(Producto producto);
    void quitarProducto(Producto producto);
}
