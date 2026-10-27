package org.abes.hrmsdatabase.service;

import java.util.List;

import org.abes.hrmsdatabase.model.Employee;
import org.abes.hrmsdatabase.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Register a new employee
    public String createnewEmployee(Employee emp) {
        if (employeeRepository.existsById(emp.getEmp_id())) {
            return "Employee ID already exists: " + emp.getEmp_id();
        }

        employeeRepository.save(emp);
        return "Employee is added successfully";
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Find employee by ID
    public Employee getEmployeeById(long empid) {
        return employeeRepository.findById(empid).orElse(null);
    }

    // Delete employee by ID
    public String deleteEmployeeById(long empid) {
        if (employeeRepository.existsById(empid)) {
            employeeRepository.deleteById(empid);
            return "Employee deleted successfully";
        }

        return "Employee is not registered with id " + empid;
    }

    // Update employee
    public String updateEmployeeById(long empid, Employee updatedEmp) {
        Employee existingEmp =
                employeeRepository.findById(empid).orElse(null);

        if (existingEmp == null) {
            return "Employee is not registered with id " + empid;
        }

        existingEmp.setEmp_name(updatedEmp.getEmp_name());
        existingEmp.setStack(updatedEmp.getStack());
        existingEmp.setDesignation(updatedEmp.getDesignation());

        employeeRepository.save(existingEmp);

        return "Employee updated successfully";
    }
}