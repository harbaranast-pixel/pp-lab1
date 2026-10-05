public class Mechanic {
    private String name;
    private Specialization specialization;
    private Order activeOrder;

    public String getName() {
        return name;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setName(String name) {
        this.name = name;
    }

public void setSpecialization(Specialization specialization) {
    this.specialization = specialization;
}

    public void startOrder(Order order) {
        if (activeOrder != null && activeOrder != order) {
            throw new IllegalStateException("A mechanic cannot work on two active orders at the same time");
        }
        activeOrder = order;
    }

    public void finishOrder(Order order) {
        if (activeOrder == order) {
            activeOrder = null;
        }
    }
}
