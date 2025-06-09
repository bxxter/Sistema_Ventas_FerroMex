package clases;

import conexionBD.Producto;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
    private JButton button;
    private JTable table;
    private int row;
    private int col;
    private CarritoListener listener;
    private String texto;

    public ButtonEditor(String texto, CarritoListener listener) {
        this.texto = texto;
        this.listener = listener;
        button = new JButton(texto);
        button.addActionListener(this);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        this.table = table;
        this.row = row;
        this.col = column;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        int idProducto = (int) table.getValueAt(row, 0);
        String nombre = (String) table.getValueAt(row, 1);
        double precio = (double) table.getValueAt(row, 2);
        int cantidad = (int) table.getValueAt(row, 3);

        Producto producto = new Producto(idProducto, nombre, precio, cantidad, "");

        if (texto.equals("+")) {
            listener.agregarProducto(producto);
        } else if (texto.equals("-")) {
            listener.quitarProducto(producto);
        }

        return texto;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fireEditingStopped();
    }
}
