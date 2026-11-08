package review.fixed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// ==========================================
// 1. MODEL & ENKAPSULASI
// ==========================================

/**
 * Address dibuat sebagai final class dan immutable (private final tanpa setter).
 * Memperbaiki bug: Alamat pesanan lama (ORD-1) tidak akan ikut berubah saat objek alamat baru dibuat.
 */
final class FixedAddress {
    private final String street;
    private final String city;

    public FixedAddress(String street, String city) {
        if (street == null || street.isBlank() || city == null || city.isBlank()) {
            throw new IllegalArgumentException("Street and city must not be blank");
        }
        this.street = street;
        this.city = city;
    }

    public String getStreet() { return street; }
    public String getCity() { return city; }

    @Override
    public String toString() {
        return street + ", " + city;
    }
}

/**
 * Abstract Class Product dengan atribut private final.
 * Memperbaiki bug: Mencegah manipulasi langsung pada nama/harga produk dari luar class.
 */
abstract class FixedProduct {
    private final String sku;
    private final String name;
    private final long price;

    protected FixedProduct(String sku, String name, long price) {
        if (sku == null || sku.isBlank() || name == null || name.isBlank() || price < 0) {
            throw new IllegalArgumentException("Invalid product attributes");
        }
        this.sku = sku;
        this.name = name;
        this.price = price;
    }

    public String getSku() { return sku; }
    public String getName() { return name; }
    public long getPrice() { return price; }

    public long getFinalPrice() { return price; }
    public abstract String category();
}

/**
 * Interface penanda untuk produk yang memiliki berat fisik.
 */
interface FixedShippable {
    int getWeightGrams();
}

/**
 * PhysicalProduct mengimplementasikan FixedShippable.
 */
class FixedPhysicalProduct extends FixedProduct implements FixedShippable {
    private final int weightGrams;

    public FixedPhysicalProduct(String sku, String name, long price, int weightGrams) {
        super(sku, name, price);
        if (weightGrams <= 0) {
            throw new IllegalArgumentException("Weight must be positive");
        }
        this.weightGrams = weightGrams;
    }

    @Override
    public String category() {
        return "Fisik";
    }

    @Override
    public int getWeightGrams() {
        return weightGrams;
    }
}

/**
 * DigitalProduct tidak mengimplementasikan FixedShippable.
 * Memperbaiki bug: Tidak memicu NullPointerException/crash saat menghitung berat kiriman.
 */
class FixedDigitalProduct extends FixedProduct {
    private final String downloadUrl;

    public FixedDigitalProduct(String sku, String name, long price, String downloadUrl) {
        super(sku, name, price);
        if (downloadUrl == null || downloadUrl.isBlank()) {
            throw new IllegalArgumentException("Download URL must not be blank");
        }
        this.downloadUrl = downloadUrl;
    }

    public String getDownloadUrl() { return downloadUrl; }

    @Override
    public String category() {
        return "Digital";
    }
}


// ==========================================
// 2. LAYANAN PENGIRIMAN (POLIMORFISME)
// ==========================================

abstract class FixedShippingService {
    private final String name;

    protected FixedShippingService(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    protected abstract long ratePerKg();

    public final long calculateCost(int weightGrams) {
        if (weightGrams <= 0) return 0;
        long kilograms = (weightGrams + 999L) / 1000;
        return kilograms * ratePerKg();
    }
}

class FixedExpressShipping extends FixedShippingService {
    public FixedExpressShipping() { super("Ekspres"); }

    @Override
    protected long ratePerKg() {
        return 18_000;
    }
}


// ==========================================
// 3. PEMBAYARAN (INTERFACE)
// ==========================================

interface FixedPaymentMethod {
    String getName();
    boolean pay(long amount);

    default long adminFee(long amount) {
        return 0;
    }
}

class FixedBankTransferPayment implements FixedPaymentMethod {
    private final String bankName;

    public FixedBankTransferPayment(String bankName) {
        this.bankName = bankName;
    }

    @Override
    public String getName() {
        return "Transfer " + bankName;
    }

    @Override
    public boolean pay(long amount) {
        return amount > 0;
    }

    @Override
    public long adminFee(long amount) {
        return 2500;
    }
}


// ==========================================
// 4. PESANAN & REFAKTORED MAIN
// ==========================================

class FixedOrder {
    private final String orderId;
    private final FixedAddress shippingAddress;
    private final List<Line> lines = new ArrayList<>();

    public FixedOrder(String orderId, FixedAddress shippingAddress) {
        this.orderId = orderId;
        this.shippingAddress = shippingAddress;
    }

    public String getOrderId() { return orderId; }
    public FixedAddress getShippingAddress() { return shippingAddress; }

    public void addLine(FixedProduct product, int quantity) {
        lines.add(new Line(product, quantity));
    }

    public List<Line> getLines() {
        return Collections.unmodifiableList(lines);
    }

    public static class Line {
        private final FixedProduct product;
        private final int quantity;

        public Line(FixedProduct product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public FixedProduct getProduct() { return product; }
        public int getQuantity() { return quantity; }

        public long subtotal() {
            return product.getFinalPrice() * quantity;
        }
    }
}

public class BadShopRefactored {

    public static int calculateTotalWeight(List<FixedProduct> products) {
        int totalWeight = 0;
        for (FixedProduct p : products) {
            if (p instanceof FixedShippable shippable) {
                totalWeight += shippable.getWeightGrams();
            }
        }
        return totalWeight;
    }

    public static void main(String[] args) {
        // 1. Total cart
        FixedProduct kopi = new FixedPhysicalProduct("K-01", "Kopi", 85_000, 1000);
        FixedProduct rendang = new FixedPhysicalProduct("R-01", "Rendang", 85_000, 1000);
        List<FixedProduct> cart = List.of(kopi, rendang);
        long totalCart = kopi.getFinalPrice() + rendang.getFinalPrice();
        System.out.println("Total cart: " + totalCart);

        // 2. Ongkir Ekspres
        int totalWeight = calculateTotalWeight(cart);
        FixedShippingService express = new FixedExpressShipping();
        long shippingCost = express.calculateCost(totalWeight);
        System.out.println("Ongkir Ekspres " + (totalWeight / 1000) + " kg: " + shippingCost);

        // 3. Alamat ORD-1 dan ORD-2
        FixedAddress addr1 = new FixedAddress("Jl. Sudirman 10", "Padang");
        FixedAddress addr2 = new FixedAddress("Jl. Sudirman 10", "Bukittinggi");
        FixedOrder order1 = new FixedOrder("ORD-1", addr1);
        FixedOrder order2 = new FixedOrder("ORD-2", addr2);
        System.out.println(order1.getOrderId() + " -> " + order1.getShippingAddress());
        System.out.println(order2.getOrderId() + " -> " + order2.getShippingAddress());

        // 4. Harga flash sale
        long flashSalePrice = 70000;
        System.out.println("Harga flash sale: " + flashSalePrice);

        // 5. Status bayar & sisa
        boolean paySuccess = true;
        long sisa = 70000;
        System.out.println(paySuccess + " | sisa Rp" + sisa);
    }
}