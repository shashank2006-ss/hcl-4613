package com.sampel.notificationservice;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    static void main(String[] args) {
        AnnotationConfigApplicationContext cts=new AnnotationConfigApplicationContext(AppConfig.class);
        Student st1=cts.getBean(Student.class);
        Student st2=cts.getBean(Student.class);
        System.out.println(st1.hashCode());
        System.out.println(st2.hashCode());
        st1.notifyStudent();
        cts.close();

    }
}
