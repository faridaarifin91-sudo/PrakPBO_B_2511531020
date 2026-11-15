package shop.service;

import shop.exception.OutOfStockException;
import shop.exception.PaymentFailedException;
import shop.model.PhysicalProduct;
import shop.model.Product;
import shop.payment.PaymentMethod;
import shop.shipping.ShippingService;

import java.util.ArrayList;
import java.util.List;

public class CheckoutService {
    private final Inventory inventory;

    public CheckoutService(Inventory inventory) {
        if (inventory == null) {
            throw new IllegalArgumentException("Inventory is required");
        }
        this.inventory = inventory;
    }

    public long total(List<Product> items, ShippingService shipping) {
        long goodsTotal = 0;
        int totalWeightGrams = 0;
        
        for (Product p : items) {
            goodsTotal += p.getPrice();
            // Menjumlahkan berat jika produk adalah PhysicalProduct
            if (p instanceof PhysicalProduct) {
                totalWeightGrams += ((PhysicalProduct) p).getWeightGrams();
            }
        }
        
        long shippingCost = shipping != null ? shipping.calculateCost(totalWeightGrams) : 0;
        return goodsTotal + shippingCost;
    }

    /** Reserves stock, then pays. Returns the amount paid. */
    public long checkout(List<Product> items, ShippingService shipping, PaymentMethod payment)
            throws OutOfStockException, PaymentFailedException {
        if (items.isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }
        List<Product> reserved = new ArrayList<>();
        try {
            for (Product product : items) {
                inventory.reserve(product.getSku(), 1);
                reserved.add(product);
            }
            long amount = total(items, shipping);
            payment.pay(amount);
            return amount;
        } catch (OutOfStockException | PaymentFailedException e) {
            for (Product product : reserved) {
                inventory.release(product.getSku(), 1); // undo the reservation
            }
            throw e; // rethrow: the caller decides what to tell the customer
        }
    }
}