package shop.notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private final List<NotificationChannel> channels = new ArrayList<>();

    public void register(NotificationChannel channel) {
        channels.add(channel);
    }

    public int broadcast(CustomerContact contact, String message) {
        int successCount = 0;
        for (NotificationChannel channel : channels) {
            if (channel.send(contact, message)) {
                successCount++;
            }
        }
        return successCount;
    }
}