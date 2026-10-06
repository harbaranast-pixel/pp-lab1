import java.util.ArrayList;
import java.util.List;

public class CarService {
    private List<Car> cars = new ArrayList<>();
    private List<Client> clients = new ArrayList<>();
    private List<Mechanic> mechanics = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();

    public void registerCar(Car car) {
        if (car == null) return;
        boolean vinExists = cars.stream().anyMatch(c -> c.getVin_code().equals(car.getVin_code()));
        if (vinExists) {
            throw new IllegalArgumentException("Car with this VIN is already registered in the service.");
        }
        if (!cars.contains(car)) {
            cars.add(car);
        }
    }

    public void registerClient(Client client) {
        if (client != null && !clients.contains(client)) {
            clients.add(client);
        }
    }

    public void registerMechanic(Mechanic mechanic) {
        if (mechanic != null && !mechanics.contains(mechanic)) {
            mechanics.add(mechanic);
        }
    }

    public Order createOrder(Car car, Client client) {
        if (!cars.contains(car)) {
            throw new IllegalArgumentException("Cannot create order: Car is not registered in this service.");
        }
        if (!clients.contains(client)) {
            throw new IllegalArgumentException("Cannot create order: Client is not registered in this service.");
        }

        Order newOrder = new Order(car, client);
        orders.add(newOrder);
        return newOrder;
    }

    public List<Car> getCars() {
        return new ArrayList<>(cars);
    }
    public List<Client> getClients() {
        return new ArrayList<>(clients);
    }
    public List<Mechanic> getMechanics() {
        return new ArrayList<>(mechanics);
    }
    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }
}