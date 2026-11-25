import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import org.junit.jupiter.api.Test;

class SalesReviewTest {

  private final SalesReview.CalculadoraVentas calculadora = new SalesReview.CalculadoraVentas();

  @Test
  void aplicaDescuentoPorVolumenYClienteVip() {
    List<SalesReview.Venta> ventas =
        List.of(new SalesReview.Venta("Mouse", 20, 3), new SalesReview.Venta("Keyboard", 50, 7));

    assertEquals(410.00, calculadora.calcularSubtotal(ventas), 0.001);
    assertEquals(10, calculadora.contarUnidades(ventas));
    assertEquals(350.55, calculadora.calcularTotal(ventas, true), 0.001);
  }

  @Test
  void aplicaSoloDescuentoPorVolumenSiNoEsVip() {
    List<SalesReview.Venta> ventas =
        List.of(new SalesReview.Venta("Mouse", 20, 3), new SalesReview.Venta("Keyboard", 50, 7));

    assertEquals(369.00, calculadora.calcularTotal(ventas, false), 0.001);
  }

  @Test
  void noAplicaDescuentoPorVolumenConMenosDeDiezUnidades() {
    List<SalesReview.Venta> ventas =
        List.of(new SalesReview.Venta("Mouse", 20, 4), new SalesReview.Venta("Cable", 10, 5));

    assertEquals(130.00, calculadora.calcularTotal(ventas, false), 0.001);
    assertEquals(123.50, calculadora.calcularTotal(ventas, true), 0.001);
  }

  @Test
  void imprimeResumenConValoresEsperados() {
    List<SalesReview.Venta> ventas =
        List.of(new SalesReview.Venta("Mouse", 20, 3), new SalesReview.Venta("Keyboard", 50, 7));

    ByteArrayOutputStream salida = new ByteArrayOutputStream();
    PrintStream consolaOriginal = System.out;
    try {
      System.setOut(new PrintStream(salida));
      new SalesReview().mostrarResumen(ventas, true);
    } finally {
      System.setOut(consolaOriginal);
    }

    String texto = salida.toString();
    assertTrue(texto.contains("Mouse: 60.0"));
    assertTrue(texto.contains("Keyboard: 350.0"));
    assertTrue(texto.contains("Subtotal: 410.00"));
    assertTrue(texto.contains("Total: 350.55"));
  }
}
