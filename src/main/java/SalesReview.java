import java.util.List;
import java.util.Locale;

/** Calcula totales de ventas con descuentos por volumen y cliente VIP. */
public class SalesReview {

  private static final int BULK_DISCOUNT_MIN_UNITS = 10;
  private static final double BULK_DISCOUNT_FACTOR = 0.90;
  private static final double VIP_DISCOUNT_FACTOR = 0.95;

  /** Crea una instancia del servicio de revision de ventas. */
  public SalesReview() {}

  /** Representa una linea de venta con producto, precio unitario y cantidad. */
  public static class Sale {
    private final String productName;
    private final double unitPrice;
    private final int quantity;

    /**
     * Inicializa una venta.
     *
     * @param productName nombre del producto
     * @param unitPrice precio unitario
     * @param quantity cantidad vendida
     */
    public Sale(String productName, double unitPrice, int quantity) {
      this.productName = productName;
      this.unitPrice = unitPrice;
      this.quantity = quantity;
    }

    /**
     * Devuelve el nombre del producto.
     *
     * @return nombre del producto
     */
    public String getProductName() {
      return productName;
    }

    /**
     * Devuelve el precio unitario.
     *
     * @return precio unitario
     */
    public double getUnitPrice() {
      return unitPrice;
    }

    /**
     * Devuelve la cantidad vendida.
     *
     * @return cantidad de unidades
     */
    public int getQuantity() {
      return quantity;
    }

    /**
     * Calcula el importe de la linea de venta.
     *
     * @return precio unitario multiplicado por la cantidad
     */
    public double getLineTotal() {
      return unitPrice * quantity;
    }
  }

  /**
   * Calcula el subtotal sin descuentos de una lista de ventas.
   *
   * @param sales lista de ventas
   * @return suma de los importes por producto
   */
  public double calculateSubtotal(List<Sale> sales) {
    double subtotal = 0.0;
    for (Sale sale : sales) {
      subtotal += sale.getLineTotal();
    }
    return subtotal;
  }

  /**
   * Cuenta el total de unidades vendidas.
   *
   * @param sales lista de ventas
   * @return cantidad acumulada de unidades
   */
  public int countTotalUnits(List<Sale> sales) {
    int totalUnits = 0;
    for (Sale sale : sales) {
      totalUnits += sale.getQuantity();
    }
    return totalUnits;
  }

  /**
   * Calcula el total final aplicando los descuentos vigentes.
   *
   * @param sales lista de ventas
   * @param isVipCustomer indica si el cliente tiene categoria VIP
   * @return monto final a pagar
   */
  public double calculateFinalTotal(List<Sale> sales, boolean isVipCustomer) {
    double total = calculateSubtotal(sales);
    if (countTotalUnits(sales) >= BULK_DISCOUNT_MIN_UNITS) {
      total *= BULK_DISCOUNT_FACTOR;
    }
    if (isVipCustomer) {
      total *= VIP_DISCOUNT_FACTOR;
    }
    return total;
  }

  /**
   * Imprime en consola el detalle de ventas, subtotal y total final.
   *
   * @param sales lista de ventas
   * @param isVipCustomer indica si el cliente tiene categoria VIP
   */
  public void printSalesSummary(List<Sale> sales, boolean isVipCustomer) {
    for (Sale sale : sales) {
      System.out.println(sale.getProductName() + ": " + sale.getLineTotal());
    }

    double subtotal = calculateSubtotal(sales);
    double finalTotal = calculateFinalTotal(sales, isVipCustomer);

    System.out.printf(Locale.US, "Subtotal: %.2f%n", subtotal);
    System.out.printf(Locale.US, "Total: %.2f%n", finalTotal);
  }

  /**
   * Punto de entrada de ejecucion de ejemplo.
   *
   * @param args argumentos de consola
   */
  public static void main(String[] args) {
    SalesReview review = new SalesReview();
    List<Sale> sales = List.of(new Sale("Mouse", 20, 3), new Sale("Keyboard", 50, 7));
    review.printSalesSummary(sales, true);
  }
}
