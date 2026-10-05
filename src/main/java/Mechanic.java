public class Mechanic {
    private String name;
    private Order activeOrder;

    public Mechanic(String name) {
        setName(name);
    }

    public String getName() { return name; }

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

    public void finishOrder(Order order) {
        if (activeOrder == order) {
            activeOrder = null;
        }
    }
}