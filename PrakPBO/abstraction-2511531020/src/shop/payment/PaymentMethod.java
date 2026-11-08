package shop.payment;

public interface PaymentMethod {
    String getName();
    boolean pay(long amount);

    // [T1.b] Solusi: Gunakan default method dengan return 0
    default long adminFee(long amount) {
        return 0;
    }
}