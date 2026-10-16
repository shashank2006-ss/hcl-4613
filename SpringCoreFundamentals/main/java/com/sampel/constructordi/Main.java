package com.sampel.constructordi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    static void main(String[] args) {
        ApplicationContext cts=new AnnotationConfigApplicationContext(AppConfig.class);
       Employee emp= cts.getBean(Employee.class);
       emp.getEmpAddress();
    }


}
