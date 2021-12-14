

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;

public class MainPanel extends JFrame implements Printable, Boleta {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JTextField textoNombre;
    private JTextField textoApellido;
    private JTextField textoRut;
    private JTextField textoNumero;
    private JTextField textoDireccion;
    private JTextField textoEmail;
    private JComboBox<String> comboBoxPago;
    private JButton botonAgregar;
    private JButton botonAgregarCompra;
    private JComboBox<Integer> comboBoxStock;
    private JList<Cliente> jListCliente;
    private ArrayList<Cliente> listaCliente;
    private DefaultListModel<Cliente> modeloListaCliente;
    private JList<Producto> jListInventario;
    private ArrayList<Producto> listaProducto;
    private DefaultListModel<Producto> modeloListaProducto;
    private JList<Producto> jListCompra;
    private ArrayList<Producto> listaCompra;
    private DefaultListModel<Producto> modeloListaCompra;
    private JTabbedPane tabbedPane2;
    private JList<Producto> jListProductos;
    private JTextField textoNombreProducto;
    private JTextField textoPrecioProducto;
    private JTextField textoStockProducto;
    private JTextField textoActualizarStock;
    private JButton agregarProductoButton;
    private JButton actualizarProductoButton;
    private JTextPane aquiMostraraLaBoletaTextPane;
    private JPanel imprimirPanel;
    private JButton actualizarButton;
    private JButton imprimirButton;
    private JButton eliminarSeleccionButton;
    private JComboBox<String> comboBoxActualizarStock;
    private JButton actualizarClienteButton;
    private JButton eliminarProductoButton;
    private JComboBox<String> comboBoxVendedores;
    private JButton eliminarClienteButton;


    public MainPanel() {
        //Metodos de propiedades de la ventana
        setContentPane(panel1);//el contenido del panel principal, en este caso es que que se trabajo con GUI Designer
        setTitle("Gestion Ventas LogoSur");//titulo de la ventana
        setSize(1200, 600);//alto y ancho de la ventana medido en pixeles
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//accion que se tomara al momento de cerrar la ventana
        setVisible(true);//se define si la ventana se mostrara o no
        imprimirButton.setEnabled(false);

        //Definiendo Listas
        listaCliente = new ArrayList<>();
        modeloListaCliente = new DefaultListModel<Cliente>();
        jListCliente.setModel(modeloListaCliente);

        listaProducto = new ArrayList<>();
        modeloListaProducto = new DefaultListModel<Producto>();
        jListInventario.setModel(modeloListaProducto);
        jListProductos.setModel(modeloListaProducto);

        listaCompra = new ArrayList<>();
        modeloListaCompra = new DefaultListModel<Producto>();
        jListCompra.setModel(modeloListaCompra);

        //Metodos ejecutados al inicio del programa
        crearArchivoCliente(listaCliente);
        crearArchivoProducto(listaProducto);
        actualizarListas();
        llenarComboBox();
        //Fin metodos inicio programa

//Listeners de seleccion en JList V
        jListCliente.addListSelectionListener(e -> {
            int numeroCliente = jListCliente.getSelectedIndex();
            if (numeroCliente >= 0) {
                Cliente c1 = listaCliente.get(numeroCliente);
                System.out.println(c1.toString());
                textoNombre.setText(c1.getNombre());
                textoApellido.setText(c1.getApellido());
                textoRut.setText(c1.getRut());
                textoNumero.setText(c1.getNumero());
                textoDireccion.setText(c1.getDireccion());
                textoEmail.setText(c1.getEmail());
            }

        });
        jListCliente.addListSelectionListener(e -> {
            int numeroCliente = jListCliente.getSelectedIndex();
            if (numeroCliente >= 0) {
                Cliente c1 = listaCliente.get(numeroCliente);
                textoNombre.setText(c1.getNombre());
                textoApellido.setText(c1.getApellido());
                textoRut.setText(c1.getRut());
            }

        });
        jListInventario.addListSelectionListener(e -> actualizarStockInventario());
//Fin listeners de seleccion ^
//Listeners de Botones V
        botonAgregar.addActionListener(e -> {
            crearCliente();
            modeloListaCliente.removeAllElements();
            System.out.println("Actualizando la lista");
            for (Cliente cliente : listaCliente) {
                modeloListaCliente.addElement(cliente);
            }
        });
        actualizarClienteButton.addActionListener(e -> {
            if (!jListCliente.isSelectionEmpty()) {
                Cliente c0 = jListCliente.getSelectedValue();
                c0.setNombre(textoNombre.getText());
                c0.setApellido(textoApellido.getText());
                c0.setRut(textoRut.getText());
                c0.setNumero(textoNumero.getText());
                c0.setDireccion(textoDireccion.getText());
                c0.setEmail(textoEmail.getText());
                actualizarListas();
            } else {
                JOptionPane.showMessageDialog(null, "DEBE SELECCIONAR UN CLIENTE", "Error al actualizar Cliente", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        eliminarProductoButton.addActionListener(e -> {
            if (!jListProductos.isSelectionEmpty()) {
                Producto p0 = jListProductos.getSelectedValue();
                listaProducto.remove(p0);
                actualizarListas();
                actualizarArchivos();
            } else {
                JOptionPane.showMessageDialog(null, "DEBE SELECCIONAR UN PRODUCTO PARA ELIMINAR", "Error al eliminar Producto", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        eliminarClienteButton.addActionListener(e -> {
            if (!jListCliente.isSelectionEmpty()) {
                Cliente c0 = jListCliente.getSelectedValue();
                listaCliente.remove(c0);
                actualizarListas();
                actualizarArchivos();
            } else {
                JOptionPane.showMessageDialog(null, "DEBE SELECCIONAR UN CLIENTE PARA ELIMINAR", "Error al eliminar Cliente", JOptionPane.INFORMATION_MESSAGE);
            }

        });


        //agregar produto al inventario
        agregarProductoButton.addActionListener(e -> {
            crearProducto();
            actualizarListas();
        });

        botonAgregarCompra.addActionListener(e -> {
            if (!jListInventario.isSelectionEmpty()) {
                int numeroProducto = jListInventario.getSelectedIndex();
                if (numeroProducto >= 0) {
                    int cantLlevar = (int) comboBoxStock.getSelectedItem();
                    Producto pr0 = new Producto(listaProducto.get(numeroProducto));
                    pr0.setStock(cantLlevar);
                    listaProducto.get(numeroProducto).actualizarStock(-cantLlevar);
                    listaCompra.add(pr0);
                    modeloListaCompra.addElement(pr0);
                    actualizarStockInventario();
                }
            }else{
                JOptionPane.showMessageDialog(null, "DEBE SELECCIONAR UN PRODUCTO PARA AGREGARLO AL CARRO DE COMPRA", "Error al agregar producto al carro", JOptionPane.INFORMATION_MESSAGE);
            }

        });

        //imprimir la boleta / generar PDF
        imprimirButton.addActionListener(e -> {
            actualizarArchivos();
            try {
                PrinterJob impresion = PrinterJob.getPrinterJob();
                impresion.setPrintable((graf, pagfor, index) -> {
                    if (index > 0) {
                        return NO_SUCH_PAGE;
                    }

                    Graphics2D hub = (Graphics2D) graf;
                    hub.translate(pagfor.getImageableX() + 30, pagfor.getImageableY() + 30);
                    hub.scale(1.0, 1.0);

                    aquiMostraraLaBoletaTextPane.printAll(graf);
                    return PAGE_EXISTS;
                });
                boolean imprimible = impresion.printDialog();
                if (imprimible) {
                    impresion.print();
                }
            } catch (PrinterException pex) {
                JOptionPane.showMessageDialog(null, "ERROR AL INTENTAR IMPRIMIR", "Error\n" + pex, JOptionPane.INFORMATION_MESSAGE);

            }
        });


        actualizarButton.addActionListener(e -> {
            if (!jListCliente.isSelectionEmpty()) {
                generarBoleta(aquiMostraraLaBoletaTextPane);
                imprimirButton.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "DEBE SELECCIONAR UN CLIENTE DE LA LISTA PARA CREAR UNA BOLETA", "Error al crear boleta", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        eliminarSeleccionButton.addActionListener(e -> {
            if (!jListCompra.isSelectionEmpty()) {
                Producto pr1 = Compra.cancelarProducto(listaProducto, listaCompra, jListCompra);
                listaCompra.remove(pr1);
                modeloListaCompra.removeElement(pr1);
                actualizarListas();
                actualizarArchivos();
            } else {
                JOptionPane.showMessageDialog(null, "DEBE SELECCIONAR UN ELEMENTO A ELIMINAR", "Error al eliminar Producto", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        actualizarProductoButton.addActionListener(e -> {
            if (!jListProductos.isSelectionEmpty()) {
                try {
                    String combo = (String) comboBoxActualizarStock.getSelectedItem();
                    System.out.println(combo);
                    int nuevoStock = Integer.parseInt(textoActualizarStock.getText());
                    if (combo.equals("Agregar Stock")) {
                        jListProductos.getSelectedValue().setStock(jListProductos.getSelectedValue().getStock() + nuevoStock);
                    } else {
                        jListProductos.getSelectedValue().setStock(jListProductos.getSelectedValue().getStock() + (nuevoStock * -1));
                    }
                    actualizarListas();
                    actualizarArchivos();
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "DEBE ESCRIBIR CUANTO STOCK DESEA AGREGAR/ELIMINAR", "Error al actualizar Stock", JOptionPane.INFORMATION_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(null, "DEBE SELECCIONAR UN PRODUCTO DE LA LISTA PARA ACTUALIZAR STOCK", "Error al actualizar Stock", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        //Fin de Botones ^
//Limites entrada de texto V
        textoActualizarStock.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                soloNumeros(e);
            }
        });

        textoNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                super.keyTyped(e);
                soloLetras(e);
            }

        });
        textoNumero.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                soloNumeros(e);
                if (textoNumero.getText().length() >= 11) {
                    e.consume();
                }
            }
        });
        textoApellido.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                soloLetras(e);
            }
        });
        textoPrecioProducto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                soloNumeros(e);
            }
        });
        textoStockProducto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                soloNumeros(e);
            }
        });
    }

    //aqui empiezan los metodos relacionados al manejo de objetos
    public void crearCliente() {
        if (textoNombre.getText().equals("") || textoApellido.getText().equals("") || textoRut.getText().equals("") || textoNumero.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "TIENE QUE RELLENAR TODOS LOS MARCADOS CON ( * ) PARA PODER AGREGAR AL CLIENTE", "Error al crear Cliente", JOptionPane.INFORMATION_MESSAGE);
        } else {
            Cliente c0 = new Cliente(textoNombre.getText(), textoApellido.getText(), textoRut.getText(), textoNumero.getText(), textoDireccion.getText(), textoEmail.getText());
            System.out.println(c0.toString());
            boolean existe = compararCliente(c0);
            if (!existe){
                listaCliente.add(c0);
                GestorArchivo.agregarLinea("Cliente.txt", c0.toString());
            }else{
                JOptionPane.showMessageDialog(null, "CLIENTE YA EXISTENTE", "Error al crear Cliente", JOptionPane.INFORMATION_MESSAGE);
            }

        }

    }

    public void crearProducto() {
        try {
            Producto pr0 = new Producto(textoNombreProducto.getText(), Integer.parseInt(textoPrecioProducto.getText()), Integer.parseInt(textoStockProducto.getText()));
            System.out.println(pr0.toString());
            boolean existe = compararProducto(pr0);
            if (!existe) {
                listaProducto.add(pr0);
                GestorArchivo.agregarLinea("Producto.txt", pr0.toString());
            }
            else{
                JOptionPane.showMessageDialog(null, "PRODUCTO YA EXISTENTE", "Error al crear Producto", JOptionPane.INFORMATION_MESSAGE);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "TIENE QUE RELLENAR LOS CAMPOS PARA AGREGAR UN PRODUCTO", "Error al crear Producto", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public void actualizarStockInventario() {
        comboBoxStock.removeAllItems();
        int numeroProducto = jListInventario.getSelectedIndex();
        if (numeroProducto >= 0) {
            Producto pr1 = listaProducto.get(numeroProducto);
            for (int i = 0; i < pr1.getStock(); i++) {
                if (comboBoxStock.getItemCount() < pr1.getStock()) {
                    comboBoxStock.addItem(i + 1);
                }
            }

        }
    }

    public void actualizarListas() {
        modeloListaProducto.removeAllElements();
        System.out.println("Actualizando lista Productos");
        for (Producto producto : listaProducto) {
            modeloListaProducto.addElement(producto);
        }
        modeloListaCliente.removeAllElements();
        System.out.println("Actualizando lista Clientes");
        for (Cliente cliente : listaCliente) {
            modeloListaCliente.addElement(cliente);
        }
    }
    public boolean compararCliente(Cliente c0){
        return listaCliente.toString().contains(c0.toString());
    }
    public boolean compararProducto(Producto p0){
        for (Producto producto: listaProducto) {
            if (producto.getNombre().equals(p0.getNombre()) && producto.getPrecio() == p0.getPrecio()) {
                return true;
            }
        }
        return false;
    }
    //aqui terminan los metodos relacionados al manejo de objetos

    //aqui empiezan los metodos relacionados a los archivos
    public void crearArchivoProducto(ArrayList<Producto> listaProducto) {
        if (GestorArchivo.leerArchivo("Producto.txt").length() == 0) {
            GestorArchivo.crearArchivo("Producto.txt", "");
        } else {
            String[] datos = GestorArchivo.leerArchivo("Producto.txt").split("\n");
            for (int i = 0; i < datos.length - 1; i++) {
                Producto p1 = new Producto(datos[i + 1]);
                listaProducto.add(p1);
            }
        }
    }

    public void crearArchivoCliente(ArrayList<Cliente> listaCliente) {
        if (GestorArchivo.leerArchivo("Cliente.txt").length() == 0) {
            GestorArchivo.crearArchivo("Cliente.txt", "");
        } else {
            String[] datos = GestorArchivo.leerArchivo("Cliente.txt").split("\n");
            for (int i = 0; i < datos.length - 1; i++) {
                Cliente c1 = new Cliente(datos[i + 1]);
                listaCliente.add(c1);
            }
        }
    }

    public void actualizarArchivos() {
        GestorArchivo.crearArchivo("Producto.txt", "");
        for (Producto producto : listaProducto) {
            GestorArchivo.agregarLinea("Producto.txt", producto.toString());
        }
        GestorArchivo.crearArchivo("Cliente.txt", "");
        for (Cliente cliente : listaCliente) {
            GestorArchivo.agregarLinea("Cliente.txt", cliente.toString());
        }

    }
    //aqui terminan metodos relacionados a los archivos

    //Aqui empiezan Metodos Boleta
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        return 0;
    }

    @Override
    public void generarBoleta(JTextPane panelBoleta) {
        String panel = agregarTextoBoleta();
        panelBoleta.setText(panel);
    }

    @Override
    public String agregarTextoBoleta() {
        String textoBoleta = "---------------------------------------------\n";
        textoBoleta = textoBoleta + "               Boleta LogoSur\n";
        textoBoleta = agregarLinea(textoBoleta, agregarLineaCliente());
        textoBoleta = agregarLinea(textoBoleta, agregarLineaProducto());
        textoBoleta = textoBoleta + "\nMetodo de pago: " + comboBoxPago.getSelectedItem() + "\n";
        textoBoleta = textoBoleta + "Total :                    " + getTotal(listaCompra) + "$\n";
        textoBoleta = textoBoleta + agregarLineaVendedor();
        textoBoleta = textoBoleta + "---------------------------------------------\n";
        return textoBoleta;
    }

    @Override
    public String agregarLinea(String stringBase, String nuevaLinea) {
        stringBase = stringBase + "\n" + nuevaLinea;
        return stringBase;
    }

    public String agregarLineaVendedor() {
        ArrayList<Vendedor> listaVendedores = new ArrayList<Vendedor>();
        crearVendedores(listaVendedores);
        int indexVendedor = comboBoxVendedores.getSelectedIndex();
        String lineaVendedor = "\nVendedor :\n"
                + listaVendedores.get(indexVendedor).getNombre() + " " + listaVendedores.get(indexVendedor).getApellido() + "\n"
                + listaVendedores.get(indexVendedor).getRut() + " - " + listaVendedores.get(indexVendedor).getEmail() + "\n";
        return lineaVendedor;

    }

    public String agregarLineaProducto() {
        String boletaProductos = "";
        for (Producto compra : listaCompra) {
            String txtProducto = compra.getStock() + "x " + compra.getNombre() + " - " + compra.getPrecio() * compra.getStock() + "$";
            boletaProductos = boletaProductos + txtProducto + "\n";
        }
        return boletaProductos;
    }

    public String agregarLineaCliente() {
        String s = jListCliente.getSelectedValue().getNombre() + " "
                + jListCliente.getSelectedValue().getApellido() + "\n"
                + jListCliente.getSelectedValue().getRut() + " / +"
                + jListCliente.getSelectedValue().getNumero() + "\n"
                + jListCliente.getSelectedValue().getDireccion() + "\n"
                + jListCliente.getSelectedValue().getEmail() + "\n";
        return s;

    }

    public int getTotal(ArrayList<Producto> listaCompra) {
        int total = 0;
        for (Producto compra : listaCompra) {
            total = total + (compra.getStock() * compra.getPrecio());
        }
        return total;
    }
    //Aqui terminan metodos boleta

    //Aqui hay metodos para restringir entrada de texto
    public void soloLetras(KeyEvent e) {
        int tecla = e.getKeyChar();
        boolean mayusculas = tecla >= 65 && tecla <= 90;
        boolean minusculas = tecla >= 97 && tecla <= 122;
        if (!(minusculas || mayusculas)) {
            e.consume();
        }
    }

    public void soloNumeros(KeyEvent e) {
        int tecla = e.getKeyChar();
        boolean numeros = tecla >= 48 && tecla <= 57;
        if (!numeros) {
            e.consume();
        }

    }
    //Aqui terminan los metodos para restringir la entrada de texto

    //Aqui empiezan metodos para crear vendedores predefinidos y llenar la combobox
    public void crearVendedores(ArrayList<Vendedor> listaVendedor) {
        Vendedor maxV = new Vendedor("Maximiliano", "Cortes", "20.835.763-8", "max@logosur.cl");
        listaVendedor.add(maxV);
        Vendedor cristobalV = new Vendedor("Cristobal", "Matus", "20.080.132-5", "cristobal@logosur.cl");
        listaVendedor.add(cristobalV);
        Vendedor jorgeV = new Vendedor("Jorge", "Cisterna", "20.911.122-6", "jorge@logosur.cl");
        listaVendedor.add(jorgeV);
    }

    public void llenarComboBox() {
        ArrayList<Vendedor> listaVendedores = new ArrayList<Vendedor>();
        crearVendedores(listaVendedores);
        for (Vendedor vendedor : listaVendedores) {
            comboBoxVendedores.addItem(vendedor.getNombre());

        }
    }
    //terminan metodos vendedores predefinidos
}
