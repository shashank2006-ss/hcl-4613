package org.abesproject.hclhrms.repository;

import org.abesproject.hclhrms.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepo {
    String addemp(Employee emp);
String addEmpbulk(List<Employee>bulkEmp);
    List<Employee>getEmp();



    Employee getEmpById(long empid);
    String deleteById(long empid);
    String updateById(long emp,Employee updateEmp);

}
