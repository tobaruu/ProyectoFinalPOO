public class Producto {
    private String nombre;
    private int precio, stock, idProducto;

    public Producto(String nombre, int precio, int stock) {
        this.idProducto = generarID();
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public Producto(Producto prx) {
        this.idProducto = prx.idProducto;
        this.nombre = prx.nombre;
        this.precio = prx.precio;
        this.stock = prx.stock;

    }

    public Producto(String desdeArchivo) {
        this.idProducto = Integer.parseInt(desdeArchivo.split(":")[0]);
        this.nombre = desdeArchivo.split(":")[1];
        this.precio = Integer.parseInt(desdeArchivo.split(":")[2]);
        this.stock = Integer.parseInt(desdeArchivo.split(":")[3]);
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void actualizarStock(int cantidad) {
        setStock(stock + cantidad);
    }

    public int getStock() {
        return stock;
    }

    public int generarID() {
        String idString = String.valueOf((System.currentTimeMillis()));
        String idSubString = idString.substring(7);
        int id = Integer.parseInt(idSubString);
        return this.idProducto = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public int getIdProducto() {
        return idProducto;
    }

    @Override
    public String toString() {
        return idProducto + ":" + nombre + ":" + precio + ":" + stock;
    }
}
