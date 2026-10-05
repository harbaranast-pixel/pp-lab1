import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private Car car;
    private List<Task> task;
    private Mechanic mechanic;
    private Client client;
    private StatusOrder status = StatusOrder.CREATED;

    public Car getCar() {
        return car;
    }

    public StatusOrder getStatus() {
        return status;
    }

    public Client getClient() {
        return client;
    }

    public List<Task> getTask() {
        return new ArrayList<>(task);
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    public BigDecimal calculateTotalCost() {
        BigDecimal total = BigDecimal.ZERO;
        if (task == null) {
            return total;
        }
        for (Task orderTask : task) {
            total = total.add(orderTask.getDetail_price()).add(orderTask.getWork_price());
        }
        return total;
    }

    public void setStatus(StatusOrder status) {
        boolean validTransition = this.status != null && switch (this.status) {
            case CREATED -> status == StatusOrder.DIAGNOSED || status == StatusOrder.CANCELLED;
            case DIAGNOSED -> status == StatusOrder.APPROVED || status == StatusOrder.CANCELLED;
            case APPROVED -> status == StatusOrder.IN_PROGRESS || status == StatusOrder.CANCELLED;
            case IN_PROGRESS -> status == StatusOrder.COMPLETED;
            case COMPLETED, CANCELLED -> false;
        };
        if (!validTransition) {
            throw new IllegalStateException(
                    "Invalid order status transition from " + this.status + " to " + status);
        }
        if (status == StatusOrder.APPROVED && (task == null || task.isEmpty())) {
            throw new IllegalStateException("An order must have at least one task before it can be approved");
        }
        if (status == StatusOrder.IN_PROGRESS) {
            if (this.status != StatusOrder.APPROVED) {
                throw new IllegalStateException("An order must be approved before it can start");
            }
            if (mechanic == null) {
                throw new IllegalStateException("A mechanic must be appointed before an order can start");
            }
        }
        if (status == StatusOrder.COMPLETED) {
            if (task == null || task.isEmpty()) {
                throw new IllegalStateException("An order must have at least one task before it can be completed");
            }
            for (Task orderTask : task) {
                if (orderTask.getStatus() != StatusOrder.COMPLETED) {
                    throw new IllegalStateException("All tasks must be completed before the order can be completed");
                }
            }
        }
        if (status == StatusOrder.IN_PROGRESS) {
            mechanic.startOrder(this);
        }
        boolean wasInProgress = this.status == StatusOrder.IN_PROGRESS;
        this.status = status;
        if (wasInProgress && status != StatusOrder.IN_PROGRESS && mechanic != null) {
            mechanic.finishOrder(this);
        }
    }

    public void setCar(Car car) {
        if (car == null || !car.isRegistered()) {
            throw new IllegalArgumentException("An order must be assigned to a registered car");
        }
        if (this.car != null && this.car != car) {
            throw new IllegalStateException("An order cannot be reassigned to another car");
        }
        this.car = car;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setMechanic(Mechanic mechanic) {
        if (this.status == StatusOrder.IN_PROGRESS) {
            if (mechanic == null) {
                throw new IllegalArgumentException("An active order must have an appointed mechanic");
            }
            if (this.mechanic == mechanic) {
                return;
            }
            mechanic.startOrder(this);
            Mechanic previousMechanic = this.mechanic;
            this.mechanic = mechanic;
            if (previousMechanic != null) {
                previousMechanic.finishOrder(this);
            }
            return;
        }
        this.mechanic = mechanic;
    }

    public void setTask(List<Task> task) {
        this.task = task;
    }
}
