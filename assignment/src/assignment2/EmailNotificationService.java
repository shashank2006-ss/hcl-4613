package assignment2;

import org.springframework.stereotype.Component;

@Component
public class EmailNotificationService implements NotificationService {
    @Override
    public void sendMessage(String message) {
        System.out.println("Email notification: " + message);
    }
}
