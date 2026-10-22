package org.abesproject.hclhrms.repository;

import org.abesproject.hclhrms.entity.Employee;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmpRepoImp implements EmployeeRepo {

List<Employee> emplist=new ArrayList<>();
    @Override
    public String addemp(Employee emp) {
        emplist.add(emp);

        return "Employee Added Successfully";
    }
    @Override
    public String addEmpbulk(List<Employee>bulkEmp){
       List<Employee>emplist= List.of(new Employee(19221,"ram","test engi","java","abc@gmail.com"),new Employee(17870,"kans","SdE","c++","cdf@gmail.com"),new Employee(198292,"saya","SDET","Java","hgs@gmail.com"));

        return "Bulk employees added successfully" ;
    }

    @Override
    public List<Employee> getEmp() {
        return emplist;
    }

    @Override
    public Employee getEmpById(long empid) {
       return emplist.stream()
                .filter(e->e.getEmp_id()==empid)
                .findFirst().orElse(null);

    }

    @Override
    public String deleteById(long empid) {
        return "";
    }

    @Override
    public String updateById(long emp, Employee updateEmp) {
        return "";
    }
}
