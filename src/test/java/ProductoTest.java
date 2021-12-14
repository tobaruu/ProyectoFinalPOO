import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductoTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void actualizarStock() {
        Producto test = new Producto("galleta", 1000, 5);
        test.actualizarStock(5);
        assertEquals(10, test.getStock());
        test.actualizarStock(-3);
        assertEquals(7, test.getStock());
    }

    @Test
    void generarID() {
        Producto pr0 = new Producto("ola", 10, 10);
        int id = pr0.getIdProducto();
        System.out.println(pr0.getIdProducto());
        assertNotEquals(230403.0, id);
    }
}