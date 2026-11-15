package shop.io;

import shop.exception.CatalogLoadException;
import shop.model.PhysicalProduct;
import shop.model.Product;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CatalogReader {
    private static final Logger LOG = Logger.getLogger(CatalogReader.class.getName());
    private int skippedCount = 0;

    public int getSkippedCount() {
        return skippedCount;
    }

    public List<Product> load(Path file) throws CatalogLoadException {
        List<Product> products = new ArrayList<>();
        skippedCount = 0;
        int lineNumber = 0;

        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                try {
                    String[] parts = line.split(";");
                    if (parts.length != 4) {
                        throw new IllegalArgumentException("expected 4 fields, found " + parts.length);
                    }
                    String sku = parts[0].trim();
                    String name = parts[1].trim();
                    long price = Long.parseLong(parts[2].trim());
                    int weight = Integer.parseInt(parts[3].trim());

                    products.add(new PhysicalProduct(sku, name, price, weight));
                } catch (IllegalArgumentException e) {
                    skippedCount++;
                    LOG.warning("Baris " + lineNumber + " dilewati: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new CatalogLoadException("Katalog gagal dibaca: " + file, e);
        }

        return products;
    }
}