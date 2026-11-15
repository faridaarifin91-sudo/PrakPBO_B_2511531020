package shop.payment;

import shop.exception.InsufficientBalanceException;

public class EWalletPayment implements PaymentMethod {
    private final String name;
    private long balance;

    public EWalletPayment(String name, long balance) {
        this.name = name;
        this.balance = balance;
    }

    @Override
    public String getName() {
        return "E-Wallet " + name;
    }

    public long getBalance() {
        return balance;
    }

    @Override
    public void pay(long amount) throws InsufficientBalanceException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (amount > balance) {
            throw new InsufficientBalanceException(amount, balance);
        }
        balance -= amount;
    }
}