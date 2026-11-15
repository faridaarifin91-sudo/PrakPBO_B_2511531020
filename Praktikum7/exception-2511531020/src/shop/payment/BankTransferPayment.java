
package shop.payment;

import shop.exception.PaymentFailedException;

public class BankTransferPayment implements PaymentMethod {

    private final String bankName;
    private boolean online;

    public BankTransferPayment(String bankName, boolean online) {
        this.bankName = bankName;
        this.online = online;
    }

    public BankTransferPayment(String bankName) {
        this(bankName, false);
    }

    // Method untuk mengubah status online
    public void setOnline(boolean online) {
        this.online = online;
    }

    @Override
    public String getName() {
        return "Bank " + bankName;
    }

    @Override
    public void pay(long amount) throws PaymentFailedException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        if (!online) {
            throw new PaymentFailedException(
                bankName + " sedang gangguan, coba lagi nanti"
            );
        }
    }
}
