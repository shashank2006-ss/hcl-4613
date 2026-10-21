package com.sampel.notificationservice;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component

public class EmailService implements NotificationService {

    @Override
    public void sendMgs(String msg) {
        System.out.println("Email Service:"+msg);
    }
}
