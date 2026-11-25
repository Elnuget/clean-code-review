import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import org.junit.jupiter.api.Test;

class SalesReviewTest {

  @Test
  void aplicaDescuentoPorVolumenYClienteVip() {
    SalesReview review = new SalesReview();
    List<SalesReview.Sale> sales =
        List.of(new SalesReview.Sale("Mouse", 20, 3), new SalesReview.Sale("Keyboard", 50, 7));

    assertEquals(410.00, review.calculateSubtotal(sales), 0.001);
    assertEquals(10, review.countTotalUnits(sales));
    assertEquals(350.55, review.calculateFinalTotal(sales, true), 0.001);
  }

  @Test
  void aplicaSoloDescuentoPorVolumenSiNoEsVip() {
    SalesReview review = new SalesReview();
    List<SalesReview.Sale> sales =
        List.of(new SalesReview.Sale("Mouse", 20, 3), new SalesReview.Sale("Keyboard", 50, 7));

    assertEquals(369.00, review.calculateFinalTotal(sales, false), 0.001);
  }

  @Test
  void noAplicaDescuentoPorVolumenConMenosDeDiezUnidades() {
    SalesReview review = new SalesReview();
    List<SalesReview.Sale> sales =
        List.of(new SalesReview.Sale("Mouse", 20, 4), new SalesReview.Sale("Cable", 10, 5));

    assertEquals(130.00, review.calculateFinalTotal(sales, false), 0.001);
    assertEquals(123.50, review.calculateFinalTotal(sales, true), 0.001);
  }

  @Test
  void imprimeResumenConValoresEsperados() {
    SalesReview review = new SalesReview();
    List<SalesReview.Sale> sales =
        List.of(new SalesReview.Sale("Mouse", 20, 3), new SalesReview.Sale("Keyboard", 50, 7));

    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    try {
      System.setOut(new PrintStream(buffer));
      review.printSalesSummary(sales, true);
    } finally {
      System.setOut(originalOut);
    }

    String output = buffer.toString();
    assertTrue(output.contains("Mouse: 60.0"));
    assertTrue(output.contains("Keyboard: 350.0"));
    assertTrue(output.contains("Subtotal: 410.00"));
    assertTrue(output.contains("Total: 350.55"));
  }
}
