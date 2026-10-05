import java.util.ArrayList;
import java.util.List;

public class CarService {
    private String name;
    private String phone;

    private List<Car> cars = new ArrayList<>();
    private List<Client> clients = new ArrayList<>();
    private List<Mechanic> mechanics = new ArrayList<>();

    public void registerCar(Car car) {
        if (car != null) {
            cars.add(car);
        }
    }

    public void registerClient(Client client) {
        if (client != null) {
            clients.add(client);
        }
    }

    public void registerMechanic(Mechanic mechanic) {
        if (mechanic != null) {
            mechanics.add(mechanic);
        }
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
}