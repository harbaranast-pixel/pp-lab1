import java.math.BigDecimal;

public class Certificate {
    private final String name;
    private BigDecimal workValueIncreasePercentage;

    public Certificate(String name, BigDecimal workValueIncreasePercentage) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Certificate name cannot be blank.");
        }
        if (workValueIncreasePercentage == null
                || workValueIncreasePercentage.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Work value increase percentage cannot be null or negative.");
        }
        this.name = name;
        this.workValueIncreasePercentage = workValueIncreasePercentage;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getWorkValueIncreasePercentage() {
        return workValueIncreasePercentage;
    }
}
