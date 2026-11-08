package shop.notification;

public class EmailChannel extends AbstractChannel {
    @Override
    public String channelName() { return "EMAIL"; }

    @Override
    protected String resolveRecipient(CustomerContact contact) {
        String email = contact.getEmail();
        return email.contains("@") ? email : null;
    }

    @Override
    protected boolean deliver(String recipient, String message) {
        System.out.println("[EMAIL to " + recipient + "] " + message);
        return true;
    }
}