import java.math.BigDecimal;

public class Task {
    private String description;
    private BigDecimal detailPrice;
    private BigDecimal workPrice;
    private int time;
    private boolean isCompleted;

    public Task(String description, BigDecimal detailPrice, BigDecimal workPrice) {
        setDescription(description);

        if (detailPrice == null || detailPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Detail price cannot be null or negative.");
        }
        this.detailPrice = detailPrice;

        if (workPrice == null || workPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Work price cannot be null or negative.");
        }
        this.workPrice = workPrice;
    }

    public int getTime() {
        return time;
    }

    public BigDecimal getDetailPrice() {
        return detailPrice;
    }

    public BigDecimal getWorkPrice() {
        return workPrice;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be blank.");
        }
        this.description = description;
    }

    public void setTime(int time) {
        if (time < 0) {
            throw new IllegalArgumentException("Time cannot be negative.");
        }
        this.time = time;
    }

    public void completeTask() {
        this.isCompleted = true;
    }
}