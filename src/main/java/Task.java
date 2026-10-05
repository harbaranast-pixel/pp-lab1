import java.math.BigDecimal;

public class Task {
    private String description;
    private BigDecimal detail_price = BigDecimal.ZERO;
    private BigDecimal whole_price = BigDecimal.ZERO;
    private StatusOrder status;
    private int time;

    public BigDecimal getDetail_price() {
        return detail_price;
    }

    public BigDecimal getWhole_price() {
        return whole_price;
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
        this.description = description;
    }

    public void setDetail_price(BigDecimal detail_price) {
        if (detail_price == null) {
            throw new IllegalArgumentException("Detail price cannot be null.");
        }
        if (detail_price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Detail price cannot be negative.");
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

    public void setWhole_price(BigDecimal whole_price) {
        if (whole_price == null) {
            throw new IllegalArgumentException("Whole price cannot be null.");
        }
        if (whole_price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Whole price cannot be negative.");
        }
        this.whole_price = whole_price;
    }
}
