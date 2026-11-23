package assignment1;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BonusCalculator {
    public BigDecimal calculateBonus(Employee employee) {
        BigDecimal eligibleSalary = employee.getBasic().add(employee.getHra());
        return eligibleSalary.multiply(employee.getRole().getBonusRate())
                .setScale(2, RoundingMode.HALF_UP);
    }
}
