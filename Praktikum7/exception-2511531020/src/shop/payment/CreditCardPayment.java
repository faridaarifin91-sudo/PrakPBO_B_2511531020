package shop.payment;

import shop.exception.InsufficientBalanceException;

public class CreditCardPayment implements PaymentMethod {
    private final String cardNumber;
    private final long creditLimit;
    private long usedCredit;

    public CreditCardPayment(String cardNumber, long creditLimit) {
        this.cardNumber = cardNumber;
        this.creditLimit = creditLimit;
        this.usedCredit = 0;
    }

    @Override
    public String getName() {
        return "Credit Card " + cardNumber;
    }

    @Override
    public void pay(long amount) throws InsufficientBalanceException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        long availableLimit = creditLimit - usedCredit;
        if (amount > availableLimit) {
            throw new InsufficientBalanceException(amount, availableLimit);
        }
        usedCredit += amount;
    }
    public long getUsedCredit() {
    return usedCredit;
}
}