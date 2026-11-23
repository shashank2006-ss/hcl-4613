package assignment1;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class BonusReport {
    private final BonusCalculator bonusCalculator = new BonusCalculator();

    public void printDescendingByBonus(List<Employee> employees) {
        employees.stream()
                .sorted(Comparator.comparing(bonusCalculator::calculateBonus).reversed()
                        .thenComparingInt(Employee::getId))
                .forEach(this::printEmployee);
    }

    private void printEmployee(Employee employee) {
        BigDecimal bonus = bonusCalculator.calculateBonus(employee);
        System.out.println("---------------------------------------------");
        System.out.println("Emp_id: " + employee.getId());
        System.out.println("Name: " + employee.getName());
        System.out.println("Role: " + employee.getRole().getDisplayName());
        System.out.println("Basic: Rs " + employee.getBasic().toPlainString());
        System.out.println("HRA: Rs " + employee.getHra().toPlainString());
        System.out.println("TA: Rs " + employee.getTravelAllowance().toPlainString());
        System.out.println("Special Allowance: Rs " + employee.getSpecialAllowance().toPlainString());
        System.out.println("Bonus: Rs " + bonus.toPlainString());
    }
}
