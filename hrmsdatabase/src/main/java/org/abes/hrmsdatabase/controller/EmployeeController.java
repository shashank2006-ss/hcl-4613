package org.abes.hrmsdatabase.controller;

import java.util.List;

import org.abes.hrmsdatabase.model.Employee;
import org.abes.hrmsdatabase.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/v1/user")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/")
    public String msg() {
        return "Welcome to SpringBoot App development";
    }

    // Register employee
    @PostMapping("/addemp")
    public String addnewEmployee(@RequestBody Employee emp) {
        return employeeService.createnewEmployee(emp);
    }

    // Get all employees
    @GetMapping("/allemp")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    // Find employee by ID
    @GetMapping("/findbyid/{empid}")
    public Employee getEmpById(@PathVariable long empid) {
        return employeeService.getEmployeeById(empid);
    }

    // Delete employee by ID
    @DeleteMapping("/deletebyid/{empid}")
    public String deleteById(@PathVariable long empid) {
        return employeeService.deleteEmployeeById(empid);
    }

    // Update employee
    @PutMapping("/updatebyid/{empid}")
    public String updateById(
            @PathVariable long empid,
            @RequestBody Employee emp) {
        return employeeService.updateEmployeeById(empid, emp);
    }
}