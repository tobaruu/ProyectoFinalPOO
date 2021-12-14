public class Cliente extends Persona {

    private String numero, direccion;

    public Cliente(String nombre, String apellido, String rut, String numero, String direccion, String email) {
        super(nombre, apellido, rut, email);
        this.numero = numero;
        this.direccion = direccion;
    }

    public Cliente(String desdeArchivo) {
        super(desdeArchivo.split(":")[0], desdeArchivo.split(":")[1], desdeArchivo.split(":")[2], desdeArchivo.split(":")[5]);
        this.numero = desdeArchivo.split(":")[3];
        this.direccion = desdeArchivo.split(":")[4];
    }

    public String getNumero() {
        return numero;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return getNombre() + ":" + getApellido() + ":" + getRut() + ":" + numero + ":" + direccion + ":" + getEmail();
    }

}
