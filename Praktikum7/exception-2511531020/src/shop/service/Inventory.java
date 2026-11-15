package shop.service;

import shop.exception.OutOfStockException;
import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private final Map<String, Integer> stockMap = new HashMap<>();

    public void addStock(String sku, int qty) {
        if (qty < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        stockMap.put(sku, stockMap.getOrDefault(sku, 0) + qty);
    }

    public int available(String sku) {
        return stockMap.getOrDefault(sku, 0);
    }

    public void reserve(String sku, int qty) throws OutOfStockException {
        if (qty <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        int currentStock = available(sku);
        if (qty > currentStock) {
            throw new OutOfStockException(sku, qty, currentStock);
        }
        stockMap.put(sku, currentStock - qty);
    }

    public void release(String sku, int qty) {
        if (qty <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        stockMap.put(sku, available(sku) + qty);
    }
}