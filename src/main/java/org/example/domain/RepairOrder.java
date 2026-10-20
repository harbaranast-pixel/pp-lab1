package org.example.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class RepairOrder {
    private final String id = UUID.randomUUID().toString();
    private final Car car;
    private final List<Task> tasks = new ArrayList<>();
    private Mechanic mechanic;
    private Client client;
    private StatusOrder status = StatusOrder.CREATED;

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    public RepairOrder(Car car, Client client) {
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
        tasks.add(newTask);
    }

    public BigDecimal calculateTotalCost() {
        BigDecimal total = BigDecimal.ZERO;
        for (Task task : tasks) {
            total = total.add(task.getDetailPrice()).add(task.getWorkPrice());
        }
        return total;
    }

    public void approve() {
        if (status != StatusOrder.DIAGNOSED) {
            throw new IllegalStateException("Invalid transition: Only diagnosed orders can be approved.");
        }
        if (tasks.isEmpty()) {
            throw new IllegalStateException("An order must have at least one task before it can be approved");
        }
        status = StatusOrder.APPROVED;
    }

    public void diagnose() {
        if (status != StatusOrder.CREATED) {
            throw new IllegalStateException("Invalid transition: Only created orders can be diagnosed.");
        }
        status = StatusOrder.DIAGNOSED;
    }

    public void startWork() {
        if (status != StatusOrder.APPROVED) {
            throw new IllegalStateException("An order must be approved before it can start");
        }
        if (mechanic == null) {
            throw new IllegalStateException("A mechanic must be appointed before an order can start");
        }
        mechanic.startOrder(this);
        status = StatusOrder.IN_PROGRESS;
    }

    public void complete() {
        if (status != StatusOrder.IN_PROGRESS) {
            throw new IllegalStateException("Only in-progress orders can be completed.");
        }
        for (Task task : tasks) {
            if (!task.isCompleted()) {
                throw new IllegalStateException("All tasks must be completed before the order can be completed");
            }
        }
        if (mechanic != null) {
            mechanic.finishOrder();
        }
        status = StatusOrder.COMPLETED;
    }

    public void cancel() {
        if (status == StatusOrder.COMPLETED || status == StatusOrder.CANCELLED) {
            throw new IllegalStateException("Cannot cancel an already completed or cancelled order.");
        }
        if (status == StatusOrder.IN_PROGRESS) {
            throw new IllegalStateException("Cannot cancel an order that is already in progress.");
        }
        status = StatusOrder.CANCELLED;
    }

    private void checkIsModifiable() {
        if (status == StatusOrder.COMPLETED || status == StatusOrder.CANCELLED) {
            throw new IllegalStateException("Cannot modify a completed or cancelled order.");
        }
    }

    public void setClient(Client client) {
        checkIsModifiable();
        this.client = client;
    }

    public void setMechanic(Mechanic mechanic) {
        checkIsModifiable();
        if (status == StatusOrder.IN_PROGRESS) {
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
                previousMechanic.finishOrder();
            }
            return;
        }
        this.mechanic = mechanic;
    }
}
