import java.math.BigDecimal;

public class Task {
    private String description;
    private BigDecimal detail_price;
    private BigDecimal work_price;
    private int time;
    private boolean isCompleted = false;

    public Task(String description, BigDecimal detail_price, BigDecimal work_price) {
        setDescription(description);

        if (detail_price == null || detail_price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Detail price cannot be null or negative.");
        }
        this.detail_price = detail_price;

        if (work_price == null || work_price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Work price cannot be null or negative.");
        }
        this.work_price = work_price;
    }

    public int getTime() {
        return time;
    }

    public BigDecimal getDetail_price() {
        return detail_price;
    }

    public BigDecimal getWork_price() {
        return work_price;
    }

    public boolean getIsCompleted() {
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
