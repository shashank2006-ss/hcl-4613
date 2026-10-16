package com.sampel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    static void main(String[] args) {
        ApplicationContext cts=new AnnotationConfigApplicationContext(AppConfig.class);
        Employee emp1=cts.getBean(Employee.class);
        Employee emp2=cts.getBean(Employee.class);
        System.out.println(emp1.hashCode());
        emp1.display();
        System.out.println(emp2.hashCode());
        emp2.display();
        System.out.println(emp1==emp2);
    }
}
