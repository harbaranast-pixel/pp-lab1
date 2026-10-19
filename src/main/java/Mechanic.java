import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Mechanic {
    private String name;
    private String specialization;
    private Order activeOrder;
    private final List<Certificate> certificates = new ArrayList<>();

    public Mechanic(String name, String specialization) {
        setName(name);
        setSpecialization(specialization);
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getName() { return name; }

    public List<Certificate> getCertificates() {
        return new ArrayList<>(certificates);
    }

    public void grantCertificate(Certificate certificate) {
        if (certificate == null) {
            throw new IllegalArgumentException("Certificate cannot be null.");
        }
        certificates.add(certificate);
    }

    public BigDecimal calculateWorkValue(BigDecimal baseWorkValue) {
        if (baseWorkValue == null || baseWorkValue.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Base work value cannot be null or negative.");
        }
        BigDecimal increasePercentage = certificates.stream()
                .map(Certificate::getWorkValueIncreasePercentage)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal increase = baseWorkValue.multiply(increasePercentage)
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        return baseWorkValue.add(increase);
    }

    public void setSpecialization(String specialization) {
        if (specialization == null || specialization.isBlank()) {
            throw new IllegalArgumentException("Mechanic specialization cannot be blank.");
        }
        this.specialization = specialization;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Mechanic name cannot be blank.");
        }
        this.name = name;
    }

    public void startOrder(Order order) {
        if (activeOrder != null && activeOrder != order) {
            throw new IllegalStateException("A mechanic cannot work on two active orders at the same time");
        }
        activeOrder = order;
    }

    public void finishOrder() {
        activeOrder = null;
    }
}