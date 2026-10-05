import java.math.BigDecimal;

public class Task {
    private String description;
    private BigDecimal detail_price = BigDecimal.ZERO;
    private BigDecimal work_price = BigDecimal.ZERO;
    private StatusOrder status;
    private int time;

    public Task(String description, BigDecimal detail_price, BigDecimal work_price) {
        setDescription(description);
        setDetail_price(detail_price);
        setWork_price(work_price);
    }
    public BigDecimal getDetail_price() {
        return detail_price;
    }

    public BigDecimal getWork_price() {
        return work_price;
    }

    public String getDescription() {
        return description;
    }

    public StatusOrder getStatus() {
        return status;
    }

    public int getTime() {
        return time;
    }

    public void setDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be blank.");
        }
        this.description = description;
    }

    public void setDetail_price(BigDecimal detail_price) {
        if (detail_price == null || detail_price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Detail price cannot be null or negative.");
        }
        this.detail_price = detail_price;
    }

    public void setStatus(StatusOrder status) {
        if (status == StatusOrder.IN_PROGRESS && this.status != StatusOrder.APPROVED) {
            throw new IllegalStateException("A task must be approved before it can start");
        }
        this.status = status;
    }

    public void setTime(int time) {
        if (time < 0) {
            throw new IllegalArgumentException("Time cannot be negative.");
        }
        this.time = time;
    }

    public void setWork_price(BigDecimal work_price) {
        if (work_price == null || work_price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Work price cannot be null or negative.");
        }
        this.work_price = work_price;
    }
}
