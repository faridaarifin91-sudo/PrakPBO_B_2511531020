package shop;

public class StudentInfo {
    public static final String NIM = "2511531020";
    public static final String NAME = "Dinda Amelia";

    // Bagian Wallet / Saldo E-Wallet
    public static long walletBalance() {
        return 200000;
    }

    public static long walletBalance(String nim) {
        return 200000;
    }

    // Bagian Stok Kopi (KOPI-001) - Menyediakan kopiStock dan kopiStock(String)
    public static int kopiStock() {
        return 3;
    }

    public static int kopiStock(String nim) {
        return 3;
    }

    // Bagian Stok Kopi dalam bahasa Inggris (coffeeStock) untuk jaga-jaga dipanggil NimParams
    public static int coffeeStock() {
        return 3;
    }

    public static int coffeeStock(String nim) {
        return 3;
    }

    // Bagian Stok Rendang (RDG-002)
    public static int rendangStock() {
        return 2;
    }

    public static int rendangStock(String nim) {
        return 2;
    }

    // Bagian Umum Stok Berdasarkan SKU
    public static int stockFor(String sku) {
        return switch (sku) {
            case "KOPI-001" -> 3;
            case "RDG-002" -> 2;
            default -> 5;
        };
    }

    public static int stockFor(String sku, String nim) {
        return stockFor(sku);
    }
}