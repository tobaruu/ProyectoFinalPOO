import javax.swing.*;

public interface Boleta {
    void generarBoleta(JTextPane panelBoleta);

    String agregarLinea(String stringBase, String nuevaLinea);
    String agregarTextoBoleta();

}
