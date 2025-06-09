/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.awt.Component;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

public class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer(String texto) {
        setText(texto);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
}
