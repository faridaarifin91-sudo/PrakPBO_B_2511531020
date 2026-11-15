package review;

import shop.exception.CatalogLoadException;
import shop.exception.PaymentFailedException;
import shop.exception.OutOfStockException;
import shop.payment.PaymentMethod;
import shop.service.Inventory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FragileShop {

    /** Pays an order. (Fixed in L5: throws PaymentFailedException instead of swallowing) */
    public static void payOrder(PaymentMethod payment, long amount) throws PaymentFailedException {
        payment.pay(amount);
    }

    /** Is at least one item in stock? */
    public static boolean isInStock(Inventory inventory, String sku) {
        try {
            inventory.reserve(sku, 1);
            inventory.release(sku, 1);
            return true;
        } catch (OutOfStockException e) {
            return false;
        }
    }

    /** Reads the first line (the header) of a catalog file. (Fixed in L5: includes cause e) */
    public static String readCatalogHeader(Path file) throws CatalogLoadException {
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            return reader.readLine();
        } catch (IOException e) {
            throw new CatalogLoadException("Katalog gagal dibaca: " + file, e);
        }
    }

    /** Adds items to the cart from what the customer typed. */
    public static String addToCart(Inventory inventory, String sku, String quantityText) {
        try {
            int quantity = Integer.parseInt(quantityText);
            inventory.reserve(sku, quantity);
            return "Ditambahkan " + quantity + " x " + sku;
        } catch (OutOfStockException | IllegalArgumentException e) {
            return "Gagal: " + e.getMessage();
        }
    }
}