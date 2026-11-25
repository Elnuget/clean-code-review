import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class SalesReviewTest {

    @Test
    void aplicaDescuentoPorVolumenYClienteVip() {
        SalesReview review = new SalesReview();
        List<SalesReview.Sale> sales = List.of(
            new SalesReview.Sale("Mouse", 20, 3),
            new SalesReview.Sale("Keyboard", 50, 7)
        );

        double total = review.calc(sales, true);
        assertEquals(350.55, total, 0.001);
    }

    @Test
    void aplicaSoloDescuentoPorVolumenSiNoEsVip() {
        SalesReview review = new SalesReview();
        List<SalesReview.Sale> sales = List.of(
            new SalesReview.Sale("Mouse", 20, 3),
            new SalesReview.Sale("Keyboard", 50, 7)
        );

        double total = review.calc(sales, false);
        assertEquals(369.00, total, 0.001);
    }

    @Test
    void noAplicaDescuentoPorVolumenConMenosDeDiezUnidades() {
        SalesReview review = new SalesReview();
        List<SalesReview.Sale> sales = List.of(
            new SalesReview.Sale("Mouse", 20, 4),
            new SalesReview.Sale("Cable", 10, 5)
        );

        assertEquals(130.00, review.calc(sales, false), 0.001);
        assertEquals(123.50, review.calc(sales, true), 0.001);
    }
}
