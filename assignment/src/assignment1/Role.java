package assignment1;

import java.math.BigDecimal;

public enum Role {
    MANAGER("Manager", "0.12"),
    ADMIN("Admin", "0.10"),
    ENGINEER("Engineer", "0.12"),
    ACCOUNTANT("Accountant", "0.08");

    private final String displayName;
    private final BigDecimal bonusRate;

    Role(String displayName, String bonusRate) {
        this.displayName = displayName;
        this.bonusRate = new BigDecimal(bonusRate);
    }

    public String getDisplayName() {
        return displayName;
    }

    public BigDecimal getBonusRate() {
        return bonusRate;
    }
}
