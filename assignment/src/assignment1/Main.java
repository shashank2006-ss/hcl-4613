package assignment1;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Employee> employees = List.of(
                new Employee(100, "Rahul", Role.MANAGER, 50000, 5800, 4000, 2500),
                new Employee(101, "Priya", Role.ADMIN, 52000, 5000, 3500, 2000),
                new Employee(102, "Amit", Role.ENGINEER, 45000, 6000, 3000, 1800),
                new Employee(103, "Neha", Role.ACCOUNTANT, 60000, 7000, 4000, 2200)
        );

        new BonusReport().printDescendingByBonus(employees);
    }
}
