import java.util.List;
import java.util.Locale;

public class SalesReview {

    public static class Sale {
        public String n;
        public double p;
        public int q;

        public Sale(String a, double b, int c) {
            n = a;
            p = b;
            q = c;
        }
    }

    public double calc(List<Sale> l, boolean v) {
        double t = 0;
        int n = 0;

        for (Sale s : l) {
            t += s.p * s.q;
            n += s.q;
        }

        if (n >= 10) {
            t = t * 0.90;
        }

        if (v) {
            t = t * 0.95;
        }

        return t;
    }

    public void show(List<Sale> l, boolean v) {
        double t = 0;
        int n = 0;

        // Iterate through sales
        for (Sale s : l) {
            t += s.p * s.q;
            n += s.q;
            System.out.println(
                s.n + ": " + (s.p * s.q)
            );
        }

        double x = t;

        // Apply discounts
        if (n >= 10) {
            x = x * 0.90;
        }

        if (v) {
            x = x * 0.95;
        }

        System.out.printf(
            Locale.US, "Subtotal: %.2f%n", t
        );

        System.out.printf(
            Locale.US, "Total: %.2f%n", x
        );
    }

    public static void main(String[] args) {
        SalesReview r = new SalesReview();

        List<Sale> l = List.of(
            new Sale("Mouse", 20, 3),
            new Sale("Keyboard", 50, 7)
        );

        r.show(l, true);
    }
}
