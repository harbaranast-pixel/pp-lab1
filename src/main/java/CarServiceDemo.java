import java.math.BigDecimal;
import java.util.List;

public class CarServiceDemo {
    public static void main(String[] args) {
        System.out.println("POSITIVE SCENARIO: ");
        Client client = new Client();
        client.setName("Alex Morgan");
        client.setPhone("555-0100");

        Car car = new Car();
        car.setBrand("Toyota");
        car.setModel("Corolla");
        car.setYear(2022);
        car.setVin_code("DEMO-CAR-001");
        car.setOwner(client);

        Mechanic mechanic = new Mechanic();
        mechanic.setName("Jordan Lee");
        mechanic.setSpecialization(Specialization.ENGINE);

        Task oilChange = createTask("Oil change", "35.50", "10.00");
        Task brakeService = createTask("Brake service", "120.00", "45.75");

        Order completedOrder = new Order();
        completedOrder.setCar(car);
        completedOrder.setClient(client);
        completedOrder.setTask(List.of(oilChange, brakeService));
        completedOrder.setStatus(StatusOrder.DIAGNOSED);
        completedOrder.setStatus(StatusOrder.APPROVED);
        completedOrder.setMechanic(mechanic);
        completedOrder.setStatus(StatusOrder.IN_PROGRESS);
        oilChange.setStatus(StatusOrder.COMPLETED);
        brakeService.setStatus(StatusOrder.COMPLETED);
        completedOrder.setStatus(StatusOrder.COMPLETED);
        System.out.println("Total cost: " + completedOrder.calculateTotalCost());

        System.out.println("\nNEGATIVE 1: APPROVE EMPTY ORDER ");
        try {
            Order emptyOrder = new Order();
            emptyOrder.setStatus(StatusOrder.DIAGNOSED);
            emptyOrder.setStatus(StatusOrder.APPROVED);
        } catch (IllegalStateException exception) {
            System.out.println(exception.getMessage());
        }

        System.out.println("\nNEGATIVE 2: START WITHOUT A MECHANIC ");
        try {
            Order orderWithoutMechanic = new Order();
            orderWithoutMechanic.setTask(List.of(createTask("Inspection", "20.00", "0.00")));
            orderWithoutMechanic.setStatus(StatusOrder.DIAGNOSED);
            orderWithoutMechanic.setStatus(StatusOrder.APPROVED);
            orderWithoutMechanic.setStatus(StatusOrder.IN_PROGRESS);
        } catch (IllegalStateException exception) {
            System.out.println(exception.getMessage());
        }

        System.out.println("\nNEGATIVE 3: ASSIGN MECHANIC TO TWO ACTIVE ORDERS ");
        try {
            Order firstActiveOrder = new Order();
            firstActiveOrder.setTask(List.of(createTask("Engine repair", "200.00", "80.00")));
            firstActiveOrder.setStatus(StatusOrder.DIAGNOSED);
            firstActiveOrder.setStatus(StatusOrder.APPROVED);
            firstActiveOrder.setMechanic(mechanic);
            firstActiveOrder.setStatus(StatusOrder.IN_PROGRESS);

            Order secondOrder = new Order();
            secondOrder.setTask(List.of(createTask("Tire change", "40.00", "0.00")));
            secondOrder.setStatus(StatusOrder.DIAGNOSED);
            secondOrder.setStatus(StatusOrder.APPROVED);
            secondOrder.setMechanic(mechanic);
            secondOrder.setStatus(StatusOrder.IN_PROGRESS);
        } catch (IllegalStateException exception) {
            System.out.println(exception.getMessage());
        }

        System.out.println("\nNEGATIVE 4: INVALID STATUS TRANSITION ");
        try {
            new Order().setStatus(StatusOrder.COMPLETED);
        } catch (IllegalStateException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static Task createTask(String description, String detailPrice, String wholePrice) {
        Task task = new Task();
        task.setDescription(description);
        task.setDetail_price(new BigDecimal(detailPrice));
        task.setWork_price(new BigDecimal(wholePrice));
        return task;

    }
}
