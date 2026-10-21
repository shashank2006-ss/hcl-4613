package com.sampel.notificationservice;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class WhatsaapService implements NotificationService {
    @Override
    public void sendMgs(String msg) {
        System.out.println("Whatsaap Service:"+msg);
    }
}
