package assignment1;

import java.math.BigDecimal;

public class Employee {
    private final int id;
    private final String name;
    private final Role role;
    private final BigDecimal basic;
    private final BigDecimal hra;
    private final BigDecimal travelAllowance;
    private final BigDecimal specialAllowance;

    public Employee(int id, String name, Role role, double basic, double hra,
                    double travelAllowance, double specialAllowance) {
        if (id <= 0) {
            throw new IllegalArgumentException("Employee ID must be positive.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Employee name must not be blank.");
        }
        if (role == null) {
            throw new IllegalArgumentException("Employee role must not be null.");
        }

        this.id = id;
        this.name = name;
        this.role = role;
        this.basic = amount(basic, "Basic");
        this.hra = amount(hra, "HRA");
        this.travelAllowance = amount(travelAllowance, "TA");
        this.specialAllowance = amount(specialAllowance, "Special allowance");
    }

    private static BigDecimal amount(double value, String label) {
        if (!Double.isFinite(value) || value < 0) {
            throw new IllegalArgumentException(label + " must be a non-negative amount.");
        }
        return BigDecimal.valueOf(value);
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public Role getRole() { return role; }
    public BigDecimal getBasic() { return basic; }
    public BigDecimal getHra() { return hra; }
    public BigDecimal getTravelAllowance() { return travelAllowance; }
    public BigDecimal getSpecialAllowance() { return specialAllowance; }
}
