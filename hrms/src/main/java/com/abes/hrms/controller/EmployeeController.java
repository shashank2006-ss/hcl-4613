package com.abes.hrms.controller;

import com.abes.hrms.model.DemoEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
    @Autowired
    private DemoEmployee demoEmployee;
    @GetMapping("/")
    public String welcome(){
        return "<h2 style='color:violet'>Welcome To HRMS App</h2>";
    }
    @GetMapping("/add")
    public int add(){
        System.out.println("inside /add controller point");
        return demoEmployee.add();


    }
    @GetMapping("/square")
    public double getSquareRoot() {
        return demoEmployee.squareRoot();
    }
    @GetMapping("/gtn")
    public  int gtn(){
        return demoEmployee.gtn();
    }
}
