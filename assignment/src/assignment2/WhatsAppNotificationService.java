package assignment2;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class WhatsAppNotificationService implements NotificationService {
    @Override
    public void sendMessage(String message) {
        System.out.println("WhatsApp notification: " + message);
    }
}
