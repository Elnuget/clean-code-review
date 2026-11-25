import java.util.List;
import java.util.Locale;

public class SalesReview {

  public record Venta(String producto, double precioUnitario, int cantidad) {
    public double subtotalLinea() {
      return precioUnitario * cantidad;
    }
  }

  public static class CalculadoraVentas {
    private static final int UNIDADES_MINIMAS_DESCUENTO = 10;
    private static final double FACTOR_DESCUENTO_VOLUMEN = 0.90;
    private static final double FACTOR_DESCUENTO_VIP = 0.95;

    public double calcularSubtotal(List<Venta> ventas) {
      return ventas.stream().mapToDouble(Venta::subtotalLinea).sum();
    }

    public int contarUnidades(List<Venta> ventas) {
      return ventas.stream().mapToInt(Venta::cantidad).sum();
    }

    public double calcularTotal(List<Venta> ventas, boolean esClienteVip) {
      double total = calcularSubtotal(ventas);
      if (contarUnidades(ventas) >= UNIDADES_MINIMAS_DESCUENTO) {
        total *= FACTOR_DESCUENTO_VOLUMEN;
      }
      if (esClienteVip) {
        total *= FACTOR_DESCUENTO_VIP;
      }
      return total;
    }
  }

  public void mostrarResumen(List<Venta> ventas, boolean esClienteVip) {
    CalculadoraVentas calculadora = new CalculadoraVentas();
    for (Venta venta : ventas) {
      System.out.println(venta.producto() + ": " + venta.subtotalLinea());
    }
    System.out.printf(Locale.US, "Subtotal: %.2f%n", calculadora.calcularSubtotal(ventas));
    System.out.printf(Locale.US, "Total: %.2f%n", calculadora.calcularTotal(ventas, esClienteVip));
  }

  public static void main(String[] args) {
    List<Venta> ventas = List.of(new Venta("Mouse", 20, 3), new Venta("Keyboard", 50, 7));
    new SalesReview().mostrarResumen(ventas, true);
  }
}
