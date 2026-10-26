package org.example.domain;

public class Mechanic {
    private String name;
    private String specialization;
    private RepairOrder activeOrder;

    public Mechanic(String name, String specialization) {
        setName(name);
        setSpecialization(specialization);
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getName() { return name; }

    public void setSpecialization(String specialization) {
        if (specialization == null || specialization.isBlank()) {
            throw new IllegalArgumentException("Mechanic's specialization cannot be blank.");
        }
        this.specialization = specialization;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Mechanic's name cannot be blank.");
        }
        this.name = name;
    }

    public void startOrder(RepairOrder order) {
        if (activeOrder != null && activeOrder != order) {
            throw new IllegalStateException("A mechanic cannot work on two active orders at the same time.");
        }
        activeOrder = order;
    }

    public void finishOrder() {
            activeOrder = null;
    }
}