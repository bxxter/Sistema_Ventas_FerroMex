
package frames;

import clases.Sesion;
import java.sql.*;
import clases.ButtonRenderer;
import clases.ButtonEditor;
import clases.CarritoListener;
import clases.ItemCarrito;
import componente.BotonGenerarPDF;
import conexionBD.Usuario;
import conexionBD.Categoria;
import conexionBD.CategoriaBD;
import conexionBD.Clientes;
import conexionBD.ClientesBD;
import conexionBD.Producto;
import conexionBD.ProductoBD;
import conexionBD.UsuarioBD;
import conexionBD.VentasBD;
import frames.Registro;
import frames.anadirCategoria;
import frames.anadirProducto;
import frames.editarProducto;
import frames.editarUsuario;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author azael
 */
public class ventanas extends javax.swing.JFrame {

    /**
     * Creates new form ventanas
     */
    private List<Producto> productosOriginales;
    private List<Usuario> listaUsuarios = new ArrayList<>();
    private List<Producto> listaProductos;
    private List<ItemCarrito> carrito = new ArrayList<>();



    
    public ventanas() {
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        jTabbedPane.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
        @Override
        protected int calculateTabAreaHeight(int tabPlacement, int runCount, int maxTabHeight) {
            return 0;
        }

        @Override
        protected void installDefaults() {
            super.installDefaults();
            tabAreaInsets.left = 0;
        }

        @Override
        protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
        }

        @Override
        protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
        }
    });
        
        
        
        /*Usuario usuario = Sesion.getUsuarioActual();
        if (usuario != null) {
            lblAtiende.setText("Le atiende: " + usuario.getNombre());
        } else {
            lblAtiende.setText("Le atiende: Usuario no identificado");
        }*/
        
        verificarRol();
    }
    
    
    public final void verificarRol() {
        if (Sesion.esCajero()) {
            btnEliminarProducto.setEnabled(false);
            editarProducto.setEnabled(false);
            btnAnadirProducto.setEnabled(false);
        }
    }
    
    public void agregarProductoATabla(Producto p, int cantidad) {
        DefaultTableModel modelo = (DefaultTableModel) tablaVentaBusqueda.getModel();

        for (int i = 0; i < modelo.getRowCount(); i++) {
            Integer idTabla = (Integer) modelo.getValueAt(i, 0);
            if (idTabla != null && idTabla == p.getId()) {
                Object cantidadObj = modelo.getValueAt(i, 3);
                int cantidadActual = 0;

                if (cantidadObj != null) {
                try {
                    cantidadActual = Integer.parseInt(cantidadObj.toString());
                } catch (NumberFormatException ex) {
                    cantidadActual = 0; // Valor por defecto si hay algo raro
                }
            }

                int stock = p.getStock();
                if (cantidadActual + cantidad <= stock) {
                    modelo.setValueAt(cantidadActual + cantidad, i, 3);
            } else {
                JOptionPane.showMessageDialog(this, "No hay suficiente stock para aumentar la cantidad.");
            }
            return;
        }
    }

        if (cantidad <= p.getStock()) {
            Object[] fila = {
                p.getId(),
                p.getNombre(),
                p.getPrecio(),
                cantidad
            };
            modelo.addRow(fila);
        } else {
            JOptionPane.showMessageDialog(this, "No hay suficiente stock para agregar el producto.");
        }
    }

    // METODOS ENCARGADOS DE RESTRINGIR LA EDICION EN CIERTAS COLUMNAS DE LAS TABLAS
    
    private void cargarDatosTablaProductos() {
        ProductoBD dao = new ProductoBD();
        listaProductos = dao.obtenerProductos();
        System.out.println("Cantidad de productos obtenidos: " + listaProductos.size());

        DefaultTableModel modelo = (DefaultTableModel) tablaProductos.getModel();
        modelo.setRowCount(0); // Limpiar tabla

        for (Producto p : listaProductos) {
            modelo.addRow(new Object[] {
                p.getId(),
                p.getNombre(),
                p.getPrecio(),
                p.getStock(),
                p.getCategoria()
            });
        }
    }

    
    private void configurarTablaCategorias() {
        DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"ID", "Nombre"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Ninguna celda editable
            }
        };

        jTableCategorias.setModel(modelo);
    }
    
    private void configurarTablaClientes() {
        DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Apellido", "Correo"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Por ahora, no editable
            }
        };

        tablaClientes.setModel(modelo);
    }


    private void cargarCategorias() {
        CategoriaBD categoriaBD = new CategoriaBD();
        List<Categoria> categorias = categoriaBD.obtenerCategorias(); // Obtener categorías actualizadas desde la base de datos

        DefaultTableModel modelo = (DefaultTableModel) jTableCategorias.getModel();
        modelo.setRowCount(0); // Limpiar las filas actuales de la tabla

        // Añadir las categorías más recientes a la tabla
        for (Categoria categoria : categorias) {
            Object[] row = new Object[]{
                categoria.getId(),
                categoria.getNombre()
            };
            modelo.addRow(row);
        }
    }
    
    private void cargarClientes() {
        ClientesBD clienteBD = new ClientesBD();
        List<Clientes> clientes = clienteBD.obtenerClientes();

        DefaultTableModel modelo = (DefaultTableModel) tablaClientes.getModel();
        modelo.setRowCount(0); // Limpiar tabla

        for (Clientes c : clientes) {
            modelo.addRow(new Object[]{
                c.getId(),
                c.getNombre(),
                c.getApellido(),
                c.getCorreo()
            });
        }
    }


    
    private void cargarDatosTablaUsuarios() {
        DefaultTableModel modelo = (DefaultTableModel) tablaUsuario.getModel();
        modelo.setRowCount(0);

        UsuarioBD dao = new UsuarioBD();
        listaUsuarios = dao.obtenerTodos();  // aquí guardamos la lista en el atributo

        for (Usuario u : listaUsuarios) {
            Object[] fila = {
                u.getId(),
                u.getNombre(),
                u.getEmail(),
                u.getRol()
            };
            modelo.addRow(fila);
        }
    }
    
    private void cargarDatosTablaUsuariosFiltrados(String filtro) {
        DefaultTableModel modelo = (DefaultTableModel) tablaUsuario.getModel();
        modelo.setRowCount(0);

        UsuarioBD dao = new UsuarioBD();
        List<Usuario> lista = dao.obtenerTodos();
        for (Usuario u : lista) {
            if (u.getNombre().toLowerCase().contains(filtro.toLowerCase())) {
                Object[] fila = {
                    u.getId(),
                    u.getNombre(),
                    u.getEmail(),
                    u.getRol()
                };
                modelo.addRow(fila);
            }
        }
    }
    
    
    
    public void mostrarUsuariosEnTabla(List<Usuario> usuarios, JTable tabla) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0); // Limpiar la tabla

        for (Usuario u : usuarios) {
            Object[] fila = {
                u.getId(),
                u.getNombre(),
                u.getEmail(),
                u.getRolId()
            };
            modelo.addRow(fila);
        }
    }
    
    private void mostrarClientesEnTabla(List<Clientes> clientes) {
        DefaultTableModel modelo = (DefaultTableModel) tablaClientes.getModel();
        modelo.setRowCount(0); // Limpiar tabla

        for (Clientes c : clientes) {
            modelo.addRow(new Object[]{
                c.getId(),
                c.getNombre(),
                c.getApellido(),
                c.getCorreo()
            });
        }
    }
    
    private void mostrarClientesEnTablaVenta(List<Clientes> clientes) {
        DefaultTableModel modelo = (DefaultTableModel) tablaClientesVenta.getModel();
        modelo.setRowCount(0); // Limpiar tabla

        for (Clientes c : clientes) {
            modelo.addRow(new Object[]{
                c.getId(),
                c.getNombre(),
                c.getApellido(),
                c.getCorreo()
            });
        }
    }

    
    private void edicionTablaCategorias() {
        DefaultTableModel modelo = (DefaultTableModel) jTableCategorias.getModel();

        modelo.addTableModelListener(e -> {
        if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
            int fila = e.getFirstRow();
            int columna = e.getColumn();

            if (columna == 1) { // Solo columna nombre
                int id = (int) modelo.getValueAt(fila, 0);
                Object valorNuevo = modelo.getValueAt(fila, 1);

                if (valorNuevo == null) return;

                String nuevoNombre = valorNuevo.toString().trim();

                // Confirmar si desea guardar
                int opcion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Deseas guardar los cambios?",
                    "Confirmación",
                    JOptionPane.YES_NO_OPTION
                );

                if (opcion != JOptionPane.YES_OPTION) {
                    cargarCategorias(); // Revertir cambios
                    return;
                }

                // Validar el nuevo nombre
                if (nuevoNombre.isEmpty() || !nuevoNombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                    JOptionPane.showMessageDialog(this, "Nombre inválido. Solo se permiten letras y espacios.");
                    cargarCategorias(); // Revertir
                    return;
                }

                // Guardar en BD
                Categoria categoria = new Categoria(id, nuevoNombre);
                CategoriaBD dao = new CategoriaBD();

                if (!dao.actualizarCategoria(categoria)) {
                    JOptionPane.showMessageDialog(this, "Error al actualizar la categoría.");
                    cargarCategorias(); // Revertir
                }
            }
        }
    });
}
    
    private void mostrarCategoriasTablaBusqueda(List<Categoria> categorias) {
        DefaultTableModel modelo = (DefaultTableModel) jTableCategorias.getModel();
        modelo.setRowCount(0); // Limpiar tabla

        for (Categoria categoria : categorias) {
            modelo.addRow(new Object[]{
                categoria.getId(),
                categoria.getNombre()
            });
        }
    }
    


    
    // VALIDACIONES
   

    /*private void limpiarCamposCategoria() {
        txtIdCategoria.setText("");
        txtNombreCategoria.setText("");
    }

    
    private void actualizarTablaCategorias() {
        CategoriaBD dao = new CategoriaBD();
        List<Categoria> categorias = dao.obtenerCategorias();  // Método que devuelve la lista de la base de datos

        DefaultTableModel modelo = (DefaultTableModel) jTableCategorias.getModel();
        modelo.setRowCount(0);  // Limpiar tabla

        for (Categoria cat : categorias) {
            modelo.addRow(new Object[]{cat.getId(), cat.getNombre()});
        }
    }*/
    
    public void mostrarEnIndice(int index) {
        if (index >= 0 && index < jTabbedPane.getTabCount()) {
            jTabbedPane.setSelectedIndex(index);
        }
        setVisible(true);
    }
    
    public void abrirVenta(){
        jTabbedPane.setSelectedIndex(0);
        configurarValidacionEdicionTabla();
        setVisible(true);
    }
    
    public void abrirEnProductos() {
        jTabbedPane.setSelectedIndex(1);
        setVisible(true);
    }
    
    
    
    public void abrirCategoria() {
        jTabbedPane.setSelectedIndex(2);
        configurarTablaCategorias();
        cargarCategorias();
        edicionTablaCategorias();
        
        setVisible(true);
    }
    
    public void abrirClientes() {
        jTabbedPane.setSelectedIndex(3);
        configurarTablaClientes();
        cargarClientes();
        setVisible(true);
    }

    
    public void abrirEnUsuario() {
        jTabbedPane.setSelectedIndex(4); 
        cargarDatosTablaUsuarios();
        setVisible(true);
    }
    
    
    
    public void mostrarProductosEnTabla(List<Producto> productos, JTable tabla) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0); // Limpiar tabla antes de agregar nuevos datos

        for (Producto p : productos) {
            Object[] fila = {
                p.getId(),
                p.getNombre(),
                p.getPrecio(),
                p.getStock(),
                p.getCategoria()
            };
            modelo.addRow(fila);
        }
    }
    
    private void configurarValidacionEdicionTabla() {
    // Variable para guardar la cantidad anterior
    final int[] cantidadAnterior = {1};

    // Guardar cantidad antes de editar con mouse
    tablaVentaBusqueda.addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            int row = tablaVentaBusqueda.getSelectedRow();
            int column = tablaVentaBusqueda.getSelectedColumn();
            if (column == 3 && row != -1) {
                Object valor = tablaVentaBusqueda.getValueAt(row, column);
                try {
                    cantidadAnterior[0] = Integer.parseInt(valor.toString());
                } catch (Exception ex) {
                    cantidadAnterior[0] = 1;
                }
            }
        }
    });
    
   

    // Guardar cantidad antes de editar con teclado
    tablaVentaBusqueda.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            int row = tablaVentaBusqueda.getSelectedRow();
            int column = tablaVentaBusqueda.getSelectedColumn();
            if (column == 3 && row != -1) {
                Object valor = tablaVentaBusqueda.getValueAt(row, column);
                try {
                    cantidadAnterior[0] = Integer.parseInt(valor.toString());
                } catch (Exception ex) {
                    cantidadAnterior[0] = 1;
                }
            }
        }
    });

    // Listener para validar cambios en la columna de cantidad
    tablaVentaBusqueda.getModel().addTableModelListener(new TableModelListener() {
        @Override
        public void tableChanged(TableModelEvent e) {
            if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 3) {
                int fila = e.getFirstRow();
                int id = (int) tablaVentaBusqueda.getValueAt(fila, 0);
                int nuevaCantidad;

                try {
                    nuevaCantidad = Integer.parseInt(tablaVentaBusqueda.getValueAt(fila, 3).toString());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Cantidad no válida.");
                    tablaVentaBusqueda.setValueAt(cantidadAnterior[0], fila, 3);
                    cancelarEdicionSiEsNecesario();
                    return;
                }

                if (nuevaCantidad <= 0) {
                    JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a 0.");
                    tablaVentaBusqueda.setValueAt(cantidadAnterior[0], fila, 3);
                    cancelarEdicionSiEsNecesario();
                    return;
                }

                ProductoBD productoBD = new ProductoBD();
                Producto p = productoBD.obtenerProductoPorId(id);
                if (p == null) {
                    JOptionPane.showMessageDialog(null, "Error al obtener producto.");
                    tablaVentaBusqueda.setValueAt(cantidadAnterior[0], fila, 3);
                    cancelarEdicionSiEsNecesario();
                    return;
                }

                if (nuevaCantidad > p.getStock()) {
                    JOptionPane.showMessageDialog(null, "Cantidad excede el stock disponible (" + p.getStock() + ").");
                    tablaVentaBusqueda.setValueAt(cantidadAnterior[0], fila, 3);
                    cancelarEdicionSiEsNecesario();
                }
            }
        }

        private void cancelarEdicionSiEsNecesario() {
            if (tablaVentaBusqueda.isEditing()) {
                tablaVentaBusqueda.getCellEditor().cancelCellEditing();
            }
        }
    });
}

    private void actualizarTotalVenta() {
        double total = 0;

        for (ItemCarrito item : carrito) {
            total += item.getCantidad() * item.getProducto().getPrecio();
        }

        lblTotalVenta.setText(String.format("Total: $ %.2f", total));
    }


    private void mostrarProductosEnTablaBusqueda(List<Producto> productos) {
        DefaultTableModel modelo = (DefaultTableModel) tablaVentaBusqueda.getModel();

        modelo.setRowCount(0);

        for (Producto p : productos) {
            modelo.addRow(new Object[]{
                p.getId(),
                p.getNombre(),
                p.getPrecio(),
                1 // cantidad inicial, editable
            });
        }
    }
    
    private void agregarAlCarrito(Producto producto) {
    // Obtener el producto actualizado desde la base de datos
    ProductoBD productoBD = new ProductoBD();
    Producto productoActualizado = productoBD.obtenerProductoPorId(producto.getId());

    if (productoActualizado == null) {
        JOptionPane.showMessageDialog(this, "No se pudo encontrar el producto en la base de datos.");
        return;
    }

    if (productoActualizado.getStock() <= 0) {
        JOptionPane.showMessageDialog(this, "Producto sin stock disponible.");
        return;
    }

    ItemCarrito itemExistente = null;
    for (ItemCarrito item : carrito) {
        if (item.getProducto().getId() == producto.getId()) {
            itemExistente = item;
            break;
        }
    }

    if (itemExistente != null) {
        if (itemExistente.getCantidad() >= productoActualizado.getStock()) {
            JOptionPane.showMessageDialog(this, "No se puede agregar más del stock disponible.");
            return;
        }
        itemExistente.setCantidad(itemExistente.getCantidad() + 1);
    } else {
        carrito.add(new ItemCarrito(productoActualizado, 1));
    }

    actualizarTablaCarrito();
}

    
    private void quitarDelCarrito(Producto producto) {
        Iterator<ItemCarrito> iterator = carrito.iterator();

        while (iterator.hasNext()) {
            ItemCarrito item = iterator.next();
            if (item.getProducto().getId() == producto.getId()) {
                int nuevaCantidad = item.getCantidad() - 1;
                if (nuevaCantidad <= 0) {
                    iterator.remove();
                } else {
                    item.setCantidad(nuevaCantidad);
                }
                break;
            }
        }

        actualizarTablaCarrito();
    }
    
    private void actualizarTablaCarrito() {
        DefaultTableModel modelo = (DefaultTableModel) tablaVenta.getModel();
        modelo.setRowCount(0);

        for (ItemCarrito item : carrito) {
            modelo.addRow(new Object[]{
                item.getProducto().getId(),
                item.getProducto().getNombre(),
                item.getCantidad(),
                item.getProducto().getPrecio(),
                item.getTotal()
            });
        }
        actualizarTotalVenta();
    }



    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtClienteVenta = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablaClientesVenta = new javax.swing.JTable();
        jPanel33 = new javax.swing.JPanel();
        lblTotalVenta = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtCodigoBusqueda = new javax.swing.JTextField();
        jPanel26 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaVentaBusqueda = new javax.swing.JTable();
        jPanel24 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaVenta = new javax.swing.JTable();
        jPanel31 = new javax.swing.JPanel();
        panelProductos = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnEliminarProducto = new javax.swing.JButton();
        editarProducto = new javax.swing.JButton();
        btnAnadirProducto = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtBusquedaProductos = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        panelCategorias = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        btnAnadirProducto1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtBusquedaCategoria = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableCategorias = new javax.swing.JTable();
        panelClientes = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        txtBuscarCliente = new javax.swing.JTextField();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaClientes = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel27 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtBusquedaUsuarios = new javax.swing.JTextField();
        jPanel29 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaUsuario = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jPanel7.setLayout(new java.awt.BorderLayout());

        jPanel18.setBackground(new java.awt.Color(0, 102, 102));
        jPanel18.setPreferredSize(new java.awt.Dimension(850, 50));

        jLabel11.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Nueva Venta");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(jLabel11)
                .addContainerGap(739, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel18, java.awt.BorderLayout.NORTH);

        jPanel20.setPreferredSize(new java.awt.Dimension(850, 250));
        jPanel20.setLayout(new java.awt.BorderLayout());

        jPanel32.setPreferredSize(new java.awt.Dimension(550, 250));

        jLabel6.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel6.setText("Asignar cliente:");

        txtClienteVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClienteVentaActionPerformed(evt);
            }
        });

        tablaClientesVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Apellido", "Correo"
            }
        ));
        jScrollPane7.setViewportView(tablaClientesVenta);

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtClienteVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtClienteVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel20.add(jPanel32, java.awt.BorderLayout.LINE_START);

        lblTotalVenta.setText("jLabel8");

        jButton1.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jButton1.setText("REALIZAR VENTA");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap(137, Short.MAX_VALUE)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                        .addComponent(lblTotalVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70))))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblTotalVenta)
                .addGap(68, 68, 68)
                .addComponent(jButton1)
                .addContainerGap(111, Short.MAX_VALUE))
        );

        jPanel20.add(jPanel33, java.awt.BorderLayout.CENTER);

        jPanel7.add(jPanel20, java.awt.BorderLayout.SOUTH);

        jPanel21.setLayout(new java.awt.BorderLayout());

        jPanel22.setPreferredSize(new java.awt.Dimension(550, 507));
        jPanel22.setLayout(new java.awt.BorderLayout());

        jPanel25.setPreferredSize(new java.awt.Dimension(550, 100));

        jLabel7.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        jLabel7.setText("Ingrese el codigo del producto:");

        txtCodigoBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoBusquedaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCodigoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        jPanel22.add(jPanel25, java.awt.BorderLayout.NORTH);

        jPanel26.setLayout(new java.awt.BorderLayout());

        tablaVentaBusqueda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "ID", "Nombre", "Precio", "Cantidad", "+", "-"
            }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Solo cantidad y botones son editables
                return column == 3 || column == 4 || column == 5;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4 || columnIndex == 5) {
                    return JButton.class;
                }
                return super.getColumnClass(columnIndex);
            }
        });
        tablaVentaBusqueda.getColumn("+").setCellRenderer(new ButtonRenderer("+"));
        tablaVentaBusqueda.getColumn("+").setCellEditor(new ButtonEditor("+", new CarritoListener() {
            @Override
            public void agregarProducto(Producto producto) {
                agregarAlCarrito(producto);
            }

            @Override
            public void quitarProducto(Producto producto) {
            }
        }));

        tablaVentaBusqueda.getColumn("-").setCellRenderer(new ButtonRenderer("-"));
        tablaVentaBusqueda.getColumn("-").setCellEditor(new ButtonEditor("-", new CarritoListener() {
            @Override
            public void agregarProducto(Producto producto) {
                // no aplica para "-"
            }

            @Override
            public void quitarProducto(Producto producto) {
                quitarDelCarrito(producto);
            }
        }));
        jScrollPane1.setViewportView(tablaVentaBusqueda);

        jPanel26.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel22.add(jPanel26, java.awt.BorderLayout.CENTER);

        jPanel21.add(jPanel22, java.awt.BorderLayout.LINE_START);

        jPanel24.setLayout(new java.awt.BorderLayout());

        jPanel30.setPreferredSize(new java.awt.Dimension(356, 570));
        jPanel30.setLayout(new java.awt.BorderLayout());

        tablaVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Cantidad", "Precio unitario", "Total"
            }
        ));
        jScrollPane6.setViewportView(tablaVenta);

        jPanel30.add(jScrollPane6, java.awt.BorderLayout.CENTER);

        jPanel24.add(jPanel30, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel24.add(jPanel31, java.awt.BorderLayout.PAGE_START);

        jPanel21.add(jPanel24, java.awt.BorderLayout.CENTER);

        jPanel7.add(jPanel21, java.awt.BorderLayout.CENTER);

        jTabbedPane.addTab("Venta", jPanel7);

        panelProductos.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(0, 102, 102));
        jPanel4.setPreferredSize(new java.awt.Dimension(850, 50));

        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Productos");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(765, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        panelProductos.add(jPanel4, java.awt.BorderLayout.NORTH);

        jPanel5.setPreferredSize(new java.awt.Dimension(850, 80));

        btnEliminarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eliminar-producto.png"))); // NOI18N
        btnEliminarProducto.setText("Eliminar Producto");
        btnEliminarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProductoActionPerformed(evt);
            }
        });

        editarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/inventario-disponible.png"))); // NOI18N
        editarProducto.setText("Editar Producto");
        editarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarProductoActionPerformed(evt);
            }
        });

        btnAnadirProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/agregar-producto.png"))); // NOI18N
        btnAnadirProducto.setText("Añadir producto");
        btnAnadirProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnadirProductoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(191, 191, 191)
                .addComponent(btnEliminarProducto)
                .addGap(40, 40, 40)
                .addComponent(editarProducto)
                .addGap(53, 53, 53)
                .addComponent(btnAnadirProducto)
                .addContainerGap(183, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editarProducto)
                    .addComponent(btnAnadirProducto))
                .addGap(27, 27, 27))
        );

        panelProductos.add(jPanel5, java.awt.BorderLayout.SOUTH);

        jPanel11.setLayout(new java.awt.BorderLayout());

        jPanel12.setPreferredSize(new java.awt.Dimension(850, 80));

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 15)); // NOI18N
        jLabel2.setText("Buscar Producto:");

        txtBusquedaProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBusquedaProductosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(117, 117, 117)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtBusquedaProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(290, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtBusquedaProductos, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel11.add(jPanel12, java.awt.BorderLayout.PAGE_START);

        jPanel13.setLayout(new java.awt.BorderLayout());

        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Precio", "Stock", "Categoria"
            }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        });
        jScrollPane4.setViewportView(tablaProductos);

        jPanel13.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jPanel11.add(jPanel13, java.awt.BorderLayout.CENTER);

        panelProductos.add(jPanel11, java.awt.BorderLayout.CENTER);

        jTabbedPane.addTab("Productos", panelProductos);

        panelCategorias.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(0, 102, 102));
        jPanel6.setPreferredSize(new java.awt.Dimension(850, 50));

        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Categoria");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel4)
                .addContainerGap(774, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        panelCategorias.add(jPanel6, java.awt.BorderLayout.NORTH);

        jPanel10.setPreferredSize(new java.awt.Dimension(850, 80));

        btnAnadirProducto1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/agregar-producto.png"))); // NOI18N
        btnAnadirProducto1.setText("Añadir categoria");
        btnAnadirProducto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnadirProducto1ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/editar.png"))); // NOI18N
        jButton4.setText("Editar categoria");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(321, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addGap(53, 53, 53)
                .addComponent(btnAnadirProducto1)
                .addGap(267, 267, 267))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAnadirProducto1)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        panelCategorias.add(jPanel10, java.awt.BorderLayout.SOUTH);

        jPanel14.setLayout(new java.awt.BorderLayout());

        jPanel15.setPreferredSize(new java.awt.Dimension(850, 80));

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 15)); // NOI18N
        jLabel1.setText("Buscar categoria:");

        txtBusquedaCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBusquedaCategoriaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(214, 214, 214)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBusquedaCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(279, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtBusquedaCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );

        jPanel14.add(jPanel15, java.awt.BorderLayout.NORTH);

        jPanel16.setLayout(new java.awt.BorderLayout());

        jTableCategorias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "Nombre"
            }
        ));
        jScrollPane2.setViewportView(jTableCategorias);

        jPanel16.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel14.add(jPanel16, java.awt.BorderLayout.CENTER);

        panelCategorias.add(jPanel14, java.awt.BorderLayout.CENTER);

        jTabbedPane.addTab("Categoria", panelCategorias);

        panelClientes.setLayout(new java.awt.BorderLayout());

        jPanel23.setBackground(new java.awt.Color(0, 102, 102));
        jPanel23.setPreferredSize(new java.awt.Dimension(850, 50));

        jLabel5.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Clientes");

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jLabel5)
                .addContainerGap(791, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelClientes.add(jPanel23, java.awt.BorderLayout.NORTH);

        jPanel1.setPreferredSize(new java.awt.Dimension(850, 80));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cerrar.png"))); // NOI18N
        jButton5.setText("Eliminar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/editar.png"))); // NOI18N
        jButton6.setText("Editar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/nueva-cuenta.png"))); // NOI18N
        jButton7.setText("Anadir");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(182, 182, 182)
                .addComponent(jButton5)
                .addGap(96, 96, 96)
                .addComponent(jButton6)
                .addGap(69, 69, 69)
                .addComponent(jButton7)
                .addContainerGap(312, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton6)
                    .addComponent(jButton7))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        panelClientes.add(jPanel1, java.awt.BorderLayout.SOUTH);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel9.setPreferredSize(new java.awt.Dimension(850, 80));

        txtBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(345, 345, 345)
                .addComponent(txtBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(246, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(txtBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel9, java.awt.BorderLayout.NORTH);

        jPanel17.setLayout(new java.awt.BorderLayout());

        tablaClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Apellido", "Correo"
            }
        ));
        jScrollPane3.setViewportView(tablaClientes);

        jPanel17.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel17, java.awt.BorderLayout.CENTER);

        panelClientes.add(jPanel2, java.awt.BorderLayout.CENTER);

        jTabbedPane.addTab("Clientes", panelClientes);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel8.setBackground(new java.awt.Color(0, 102, 102));
        jPanel8.setPreferredSize(new java.awt.Dimension(850, 50));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 950, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel8, java.awt.BorderLayout.NORTH);

        jPanel19.setPreferredSize(new java.awt.Dimension(850, 80));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/nueva-cuenta.png"))); // NOI18N
        jButton2.setText("Añadir usuario");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/editar.png"))); // NOI18N
        jButton3.setText("Actualizar Datos");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap(284, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(55, 55, 55)
                .addComponent(jButton2)
                .addGap(318, 318, 318))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel19, java.awt.BorderLayout.SOUTH);

        jPanel27.setLayout(new java.awt.BorderLayout());

        jPanel28.setPreferredSize(new java.awt.Dimension(850, 80));

        jLabel12.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel12.setText("Buscar Usuario:");

        txtBusquedaUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBusquedaUsuariosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(223, 223, 223)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBusquedaUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(185, Short.MAX_VALUE))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtBusquedaUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel27.add(jPanel28, java.awt.BorderLayout.NORTH);

        jPanel29.setLayout(new java.awt.BorderLayout());

        tablaUsuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Email", "Rol"
            }
        ));
        jScrollPane5.setViewportView(tablaUsuario);

        jPanel29.add(jScrollPane5, java.awt.BorderLayout.CENTER);

        jPanel27.add(jPanel29, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel27, java.awt.BorderLayout.CENTER);

        jTabbedPane.addTab("Usuarios", jPanel3);

        getContentPane().add(jTabbedPane);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBusquedaProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBusquedaProductosActionPerformed
        // TODO add your handling code here:
        String textoBusqueda = txtBusquedaProductos.getText().trim();
        ProductoBD productoBD = new ProductoBD();
        //List<Producto> resultados = productoBD.buscarProductosPorNombre(textoBusqueda);
        
        listaProductos = productoBD.buscarProductosPorNombre(textoBusqueda);
        mostrarProductosEnTabla(listaProductos, tablaProductos);
        txtBusquedaProductos.setText("");
    }//GEN-LAST:event_txtBusquedaProductosActionPerformed

    private void editarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarProductoActionPerformed
        // TODO add your handling code here:
        if (listaProductos == null || listaProductos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay productos cargados.");
            return;
        }

        int filaSeleccionada = tablaProductos.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto para editar.");
            return;
        }

        if (filaSeleccionada >= listaProductos.size()) {
            JOptionPane.showMessageDialog(this, "Producto inválido.");
            return;
        }

        Producto productoSeleccionado = listaProductos.get(filaSeleccionada);

        editarProducto frameEditar = new editarProducto(productoSeleccionado);
        frameEditar.setVisible(true);

        frameEditar.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                cargarDatosTablaProductos();
            }
        });
    }//GEN-LAST:event_editarProductoActionPerformed

    private void btnEliminarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProductoActionPerformed
        // TODO add your handling code here:
        int filaSeleccionada = tablaProductos.getSelectedRow();

        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(null, "Selecciona un producto para eliminar.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) tablaProductos.getModel();
        int idProducto = Integer.parseInt(modelo.getValueAt(filaSeleccionada, 0).toString());

        int confirm = JOptionPane.showConfirmDialog(null, "¿Estás seguro que quieres eliminar el producto con ID " + idProducto + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            ProductoBD productoBD = new ProductoBD();
            boolean eliminado = productoBD.eliminarProducto(idProducto);

            if (eliminado) {
                // Eliminar fila de la tabla
                modelo.removeRow(filaSeleccionada);

                if (productosOriginales != null && filaSeleccionada < productosOriginales.size()) {
                    productosOriginales.remove(filaSeleccionada);
                }

                JOptionPane.showMessageDialog(null, "Producto eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnEliminarProductoActionPerformed

    private void btnAnadirProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnadirProductoActionPerformed
        // TODO add your handling code here:
        new anadirProducto().setVisible(true);
    }//GEN-LAST:event_btnAnadirProductoActionPerformed

    private void txtCodigoBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoBusquedaActionPerformed
        // TODO add your handling code here:
         String texto = txtCodigoBusqueda.getText().trim();
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un código o nombre para buscar.");
            return;
        }

        ProductoBD productoBD = new ProductoBD();
        List<Producto> productos = productoBD.buscarProductosPorNombre(texto);  // Reutiliza método que ya tienes

        if (productos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron productos.");
            return;
        }

        mostrarProductosEnTablaBusqueda(productos);
    }//GEN-LAST:event_txtCodigoBusquedaActionPerformed

    private void btnAnadirProducto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnadirProducto1ActionPerformed
        // TODO add your handling code here:
        new anadirCategoria().setVisible(true);
    }//GEN-LAST:event_btnAnadirProducto1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        new Registro().setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtBusquedaUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBusquedaUsuariosActionPerformed
        // TODO add your handling code here:
        String textoBusqueda = txtBusquedaUsuarios.getText().trim();
        cargarDatosTablaUsuariosFiltrados(textoBusqueda);
    }//GEN-LAST:event_txtBusquedaUsuariosActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if (listaUsuarios == null || listaUsuarios.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay usuarios cargados para editar.");
            return;
        }

        int filaSeleccionada = tablaUsuario.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un usuario para editar.");
            return;
        }

        if (filaSeleccionada >= listaUsuarios.size()) {
            JOptionPane.showMessageDialog(this, "Fila seleccionada inválida.");
            return;
        }

        Usuario usuarioSeleccionado = listaUsuarios.get(filaSeleccionada);

        editarUsuario ventanaEditar = new editarUsuario(usuarioSeleccionado);
        ventanaEditar.setVisible(true);

        // refrescar tabla al cerrar 
        ventanaEditar.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                cargarDatosTablaUsuarios();
            }
        });
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtBusquedaCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBusquedaCategoriaActionPerformed
        // TODO add your handling code here:
         String textoBusqueda = txtBusquedaCategoria.getText().trim();
    
        if (textoBusqueda.isEmpty()) {
            cargarCategorias(); // Mostrar todas si el campo está vacío
        } else {
            CategoriaBD categoriaBD = new CategoriaBD();
            List<Categoria> resultados = categoriaBD.buscarCategoriasPorNombre(textoBusqueda);
            mostrarCategoriasTablaBusqueda(resultados);
        }
    }//GEN-LAST:event_txtBusquedaCategoriaActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
           int fila = jTableCategorias.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona una categoría para editar.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) jTableCategorias.getModel();
        int id = (int) modelo.getValueAt(fila, 0);
        String nombreActual = (String) modelo.getValueAt(fila, 1);

        JTextField txtNuevoNombre = new JTextField(nombreActual);
        Object[] mensaje = {
        "Editar nombre de la categoría:", txtNuevoNombre
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Editar Categoría", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String nuevoNombre = txtNuevoNombre.getText().trim();

            // Validar nombre (solo letras y espacios)
            if (nuevoNombre.isEmpty() || !nuevoNombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                JOptionPane.showMessageDialog(this, "Nombre inválido. Solo se permiten letras y espacios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Confirmar guardado
            int confirmar = JOptionPane.showConfirmDialog(this, "¿Deseas guardar los cambios?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (confirmar != JOptionPane.YES_OPTION) return;

            Categoria categoria = new Categoria(id, nuevoNombre);
            CategoriaBD dao = new CategoriaBD();

            if (dao.actualizarCategoria(categoria)) {
                JOptionPane.showMessageDialog(this, "Categoría actualizada correctamente.");
                cargarCategorias();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar la categoría.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txtBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarClienteActionPerformed
        // TODO add your handling code here:
        String texto = txtBuscarCliente.getText().trim();

        if (texto.isEmpty()) {
            cargarClientes();
        } else {
            ClientesBD clienteBD = new ClientesBD();
            List<Clientes> resultado = clienteBD.buscarClientes(texto);
            mostrarClientesEnTabla(resultado);
        }
    }//GEN-LAST:event_txtBuscarClienteActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        int fila = tablaClientes.getSelectedRow();

        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un cliente para eliminar.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) tablaClientes.getModel();
        int id = (int) modelo.getValueAt(fila, 0);
        String nombre = modelo.getValueAt(fila, 1).toString();
        String apellido = modelo.getValueAt(fila, 2).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Deseas eliminar al cliente: " + nombre + " " + apellido + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            ClientesBD dao = new ClientesBD();
            boolean eliminado = dao.eliminarCliente(id);

            if (eliminado) {
                JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.");
                cargarClientes();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el cliente.");
            }
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        JTextField txtNombre = new JTextField();
        JTextField txtApellido = new JTextField();
        JTextField txtCorreo = new JTextField();

        Object[] mensaje = {
            "Nombre:", txtNombre,
            "Apellido:", txtApellido,
            "Correo:", txtCorreo
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Nuevo Cliente", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String correo = txtCorreo.getText().trim();

            // Validaciones básicas
            if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+") || !apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                JOptionPane.showMessageDialog(this, "Nombre y Apellido solo deben contener letras.");
            return;
            }

            if (!correo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                JOptionPane.showMessageDialog(this, "Correo inválido.");
                return;
            }

            Clientes nuevo = new Clientes(nombre, apellido, correo);
            ClientesBD dao = new ClientesBD();

            if (dao.insertarCliente(nuevo)) {
                JOptionPane.showMessageDialog(this, "Cliente agregado correctamente.");
                cargarClientes(); // Recargar tabla
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar cliente.");
            }
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        int fila = tablaClientes.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un cliente para actualizar.");
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) tablaClientes.getModel();
        int id = (int) modelo.getValueAt(fila, 0);
        String nombreActual = (String) modelo.getValueAt(fila, 1);
        String apellidoActual = (String) modelo.getValueAt(fila, 2);
        String correoActual = (String) modelo.getValueAt(fila, 3);

        JTextField txtNombre = new JTextField(nombreActual);
        JTextField txtApellido = new JTextField(apellidoActual);
        JTextField txtCorreo = new JTextField(correoActual);

        Object[] mensaje = {
            "Nombre:", txtNombre,
            "Apellido:", txtApellido,
            "Correo:", txtCorreo
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Actualizar Cliente", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String nuevoNombre = txtNombre.getText().trim();
            String nuevoApellido = txtApellido.getText().trim();
            String nuevoCorreo = txtCorreo.getText().trim();

            if (!nuevoNombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+") || !nuevoApellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                JOptionPane.showMessageDialog(this, "Nombre y Apellido solo deben contener letras.");
                return;
            }

            if (!nuevoCorreo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                JOptionPane.showMessageDialog(this, "Correo inválido.");
                return;
            }

            Clientes clienteActualizado = new Clientes(id, nuevoNombre, nuevoApellido, nuevoCorreo);
            ClientesBD dao = new ClientesBD();

            if (dao.actualizarCliente(clienteActualizado)) {
                JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente.");
                cargarClientes();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar cliente.");
            }
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void txtClienteVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClienteVentaActionPerformed
        // TODO add your handling code here:
        String texto = txtClienteVenta.getText().trim();

        if (texto.isEmpty()) {
            cargarClientes();
        } else {
            ClientesBD clienteBD = new ClientesBD();
            List<Clientes> resultado = clienteBD.buscarClientes(texto);
            mostrarClientesEnTablaVenta(resultado);
        }
    }//GEN-LAST:event_txtClienteVentaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:                        
    try {
        // Validar que haya productos en carrito
        if (carrito.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El carrito está vacío.");
            return;
        }

        // Validar que haya un cliente seleccionado
        int filaCliente = tablaClientesVenta.getSelectedRow();
        if (filaCliente == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para realizar la venta.");
            return;
        }

        // Obtener id del cliente seleccionado (asumiendo columna 0)
        int idClienteSeleccionado = (int) tablaClientesVenta.getValueAt(filaCliente, 0);

        // Crear instancia de VentasBD y realizar la venta
        VentasBD ventasBD = new VentasBD();
        int idVenta = ventasBD.realizarVenta(idClienteSeleccionado, carrito);

        

        JOptionPane.showMessageDialog(this, "Venta realizada correctamente. ID venta: " + idVenta);

        // Obtener datos completos del cliente para el PDF
        ClientesBD clienteBD = new ClientesBD();
        Clientes cliente = clienteBD.obtenerClientePorId(idClienteSeleccionado);

        System.out.println("Filas en tablaVenta: " + tablaVenta.getRowCount());

        // Generar PDF con datos cliente y tabla carrito
        BotonGenerarPDF botonPDF = new BotonGenerarPDF();
        botonPDF.generarPDFDesdeVenta(
            cliente.getNombre(),
            cliente.getApellido(),
            cliente.getCorreo(),
            "FerroMex",            // Nombre local
            "Dirección local",     // Dirección local
            "Sucursal principal",  // Sucursal
            tablaVenta,             // Tu tabla con carrito
            lblTotalVenta
        );
        
        // Limpiar el carrito y actualizar tabla
        carrito.clear();
        actualizarTablaCarrito();

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error al realizar la venta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }

    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ventanas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ventanas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ventanas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ventanas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ventanas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnadirProducto;
    private javax.swing.JButton btnAnadirProducto1;
    private javax.swing.JButton btnEliminarProducto;
    private javax.swing.JButton editarProducto;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTable jTableCategorias;
    private javax.swing.JLabel lblTotalVenta;
    private javax.swing.JPanel panelCategorias;
    private javax.swing.JPanel panelClientes;
    private javax.swing.JPanel panelProductos;
    private javax.swing.JTable tablaClientes;
    private javax.swing.JTable tablaClientesVenta;
    private javax.swing.JTable tablaProductos;
    private javax.swing.JTable tablaUsuario;
    private javax.swing.JTable tablaVenta;
    private javax.swing.JTable tablaVentaBusqueda;
    private javax.swing.JTextField txtBuscarCliente;
    private javax.swing.JTextField txtBusquedaCategoria;
    private javax.swing.JTextField txtBusquedaProductos;
    private javax.swing.JTextField txtBusquedaUsuarios;
    private javax.swing.JTextField txtClienteVenta;
    private javax.swing.JTextField txtCodigoBusqueda;
    // End of variables declaration//GEN-END:variables
}
