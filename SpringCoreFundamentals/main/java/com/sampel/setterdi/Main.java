package com.sampel.setterdi;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.AnnotatedArrayType;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext cts=new AnnotationConfigApplicationContext(AppConfig.class);
            Employee emp=cts.getBean(Employee.class);
             emp.displayAddress();
            cts.close();

    }
}
