import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private Car car;
    private List<Task> tasks = new ArrayList<>();
    private Mechanic mechanic;
    private Client client;
    private StatusOrder status = StatusOrder.CREATED;

    public Order(Car car, Client client) {
        if (car == null) {
            throw new IllegalArgumentException("An order must have a car");
        }
        if (client == null) {
            throw new IllegalArgumentException("An order must have a client");
        }
        this.car = car;
        this.client = client;
    }

    public void addTask(Task newTask) {
        checkIsModifiable();
        if (newTask == null) {
            throw new IllegalArgumentException("Cannot add a null task");
        }
        this.tasks.add(newTask);
    }

    public Car getCar() {
        return car;
    }

    public StatusOrder getStatus() {
        return status;
    }

    public Client getClient() {
        return client;
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    public BigDecimal calculateTotalCost() {
        BigDecimal total = BigDecimal.ZERO;
        for (Task orderTask : tasks) {
            total = total.add(orderTask.getDetail_price()).add(orderTask.getWork_price());
        }
        return total;
    }

    public void approve() {
        if (this.status != StatusOrder.DIAGNOSED) {
            throw new IllegalStateException("Invalid transition: Only diagnosed orders can be approved.");
        }
        if (tasks == null || tasks.isEmpty()) {
            throw new IllegalStateException("An order must have at least one task before it can be approved");
        }
        this.status = StatusOrder.APPROVED;
    }

    public void diagnose() {
        if (this.status != StatusOrder.CREATED) {
            throw new IllegalStateException("Invalid transition: Only created orders can be diagnosed.");
        }
        this.status = StatusOrder.DIAGNOSED;
    }

    public void startWork() {
        if (this.status != StatusOrder.APPROVED) {
            throw new IllegalStateException("An order must be approved before it can start");
        }
        if (mechanic == null) {
            throw new IllegalStateException("A mechanic must be appointed before an order can start");
        }
        mechanic.startOrder(this);
        this.status = StatusOrder.IN_PROGRESS;
    }

    public void complete() {
        if (this.status != StatusOrder.IN_PROGRESS) {
            throw new IllegalStateException("Only in-progress orders can be completed.");
        }
        for (Task orderTask : tasks) {
            if (!orderTask.getIsCompleted()) {
                throw new IllegalStateException("All tasks must be completed before the order can be completed");
            }
        }
        if (mechanic != null) {
            mechanic.finishOrder(this);
        }
        this.status = StatusOrder.COMPLETED;
    }

    public void cancel() {
        if (this.status == StatusOrder.COMPLETED || this.status == StatusOrder.CANCELLED) {
            throw new IllegalStateException("Cannot cancel an already completed or cancelled order.");
        }
        if (this.status == StatusOrder.IN_PROGRESS) {
            throw new IllegalStateException("Cannot cancel an order that is already in progress.");
        }
        this.status = StatusOrder.CANCELLED;
    }

    private void checkIsModifiable() {
        if (status == StatusOrder.COMPLETED || status == StatusOrder.CANCELLED) {
            throw new IllegalStateException("Cannot modify a completed or cancelled order.");
        }
    }

    public void setCar(Car car) {
        checkIsModifiable();
        if (car == null) {
            throw new IllegalArgumentException("An order must be assigned to a car");
        }
        if (this.car != null && this.car != car) {
            throw new IllegalStateException("An order cannot be reassigned to another car");
        }
        this.car = car;
    }

    public void setClient(Client client) {
        checkIsModifiable();
        this.client = client;
    }

    public void setMechanic(Mechanic mechanic) {
        checkIsModifiable();
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

}
