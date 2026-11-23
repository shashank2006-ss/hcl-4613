package assignment2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Student {
    private final NotificationService notificationService;

    @Autowired
    public Student(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void notifyAssessmentScheduled() {
        notificationService.sendMessage("Your assessment is scheduled.");
    }
}
