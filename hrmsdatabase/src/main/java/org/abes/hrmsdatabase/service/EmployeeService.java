package org.abes.hrmsdatabase.service;

import org.abes.hrmsdatabase.model.Employee;
import org.abes.hrmsdatabase.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    public String createnewEmployee(){
        Employee emp1=new Employee(200l,"Ram","Java","Developer");
        employeeRepository.save(emp1);
        return "Employee is added successfully";
    }

    public Employee getEmployeeById(long empid){
        return employeeRepository.findById(empid).orElse(null);

    }
    public String deleteEmployeeById(long empid) {
        if (employeeRepository.existsById(empid)) {
            employeeRepository.deleteById(empid);
            return "Employee deleted successfully";
        }
        return "Employee is not registered with id " + empid;
    }

    public String updateEmployeeById(long empid, Employee updatedEmp) {
        Employee existingEmp = employeeRepository.findById(empid).orElse(null);
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
