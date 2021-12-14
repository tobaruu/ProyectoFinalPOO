import javax.swing.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Compra {

    private ArrayList<Producto> listaProducto;
    private ArrayList<Producto> listaCompra;

    public static Producto cancelarProducto(ArrayList<Producto> listaProducto, ArrayList<Producto> listaCompra, JList<Producto> jListCompra) {
        Producto pr0 = jListCompra.getSelectedValue();
        //pr0 = Producto a eliminar de la lista de compra, del cual hay que extraer el stock para volver a sumarlo al correspondiente en la listaProducto
        Producto pr1 = listaProducto.stream().filter(p1 -> p1.getIdProducto() == pr0.getIdProducto()).collect(Collectors.toList()).get(0);
        pr1.setStock(pr1.getStock() + pr0.getStock());
        return pr0;
    }
}
