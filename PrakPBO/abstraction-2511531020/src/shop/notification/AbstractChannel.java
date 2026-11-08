package shop.notification;

public abstract class AbstractChannel implements NotificationChannel {
    private int sentCount = 0;
    private String lastDelivered = "";

    protected abstract String resolveRecipient(CustomerContact contact);
    protected abstract boolean deliver(String recipient, String message);

    protected String prepare(String message) {
        return message;
    }

    @Override
    public final boolean send(CustomerContact contact, String message) {
        if (message == null || message.isBlank()) {
            return false;
        }
        String recipient = resolveRecipient(contact);
        if (recipient == null || recipient.isBlank()) {
            return false;
        }
        String prepared = prepare(message);
        boolean success = deliver(recipient, prepared);
        if (success) {
            sentCount++;
            lastDelivered = prepared;
        }
        return success;
    }

    public int getSentCount() { return sentCount; }
    public String getLastDelivered() { return lastDelivered; }
}