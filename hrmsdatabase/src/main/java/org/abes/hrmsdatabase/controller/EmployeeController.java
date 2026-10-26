package org.abes.hrmsdatabase.controller;

import org.abes.hrmsdatabase.model.Employee;
import org.abes.hrmsdatabase.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @GetMapping("/")
    public String msg(){
        return "<h2>Welcome to SpringBoot App development</h2>";
    }
    @GetMapping("/addemp")
    public String addnewEmployee(){
        return employeeService.createnewEmployee();
    }
    @GetMapping("findbyid/{empid}")
    public Employee getEmpById(@PathVariable long empid){
        return employeeService.getEmployeeById(empid);
    }
    @DeleteMapping("/deletebyid/{empid}")
    public String deleteById(@PathVariable long empid) {
        return employeeService.deleteEmployeeById(empid);
    }
    @PutMapping("/updatebyid/{empid}")
    public String updateById(@PathVariable long empid, @RequestBody Employee emp) {
        return employeeService.updateEmployeeById(empid, emp);
    }
}
