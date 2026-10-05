import java.math.BigDecimal;
import java.util.List;

public class CarServiceDemo {
    public static void main(String[] args) {
        System.out.println("POSITIVE SCENARIO: ");

        CarService carService = new CarService();

        Client client = new Client("Alex Morgan", "555-0100");
        carService.registerClient(client);

        Car car = new Car("DEMO-CAR-001", "Toyota", "Corolla", 2022, client);
        carService.registerCar(car);

        Mechanic mechanic = new Mechanic("Jordan Lee");
        carService.registerMechanic(mechanic);

        Order completedOrder = carService.createOrder(car, client);

        Task oilChange = new Task("Oil change", new BigDecimal("35.50"), new BigDecimal("10.00"));
        Task brakeService = new Task("Brake service", new BigDecimal("120.00"), new BigDecimal("45.75"));

        completedOrder.addTask(oilChange);
        completedOrder.addTask(brakeService);

        completedOrder.setStatus(StatusOrder.DIAGNOSED);
        completedOrder.setStatus(StatusOrder.APPROVED);
        completedOrder.setMechanic(mechanic);
        completedOrder.setStatus(StatusOrder.IN_PROGRESS);

        oilChange.setStatus(StatusOrder.COMPLETED);
        brakeService.setStatus(StatusOrder.COMPLETED);
        completedOrder.setStatus(StatusOrder.COMPLETED);

        System.out.println("Total cost: " + completedOrder.calculateTotalCost());


        System.out.println("\nNEGATIVE 1: COORDINATOR BLOCKS UNREGISTERED CAR ");
        try {
            Client unregisteredClient = new Client("Ghost", "000");
            Car unregisteredCar = new Car("GHOST-VIN", "BMW", "X5", 2020, unregisteredClient);
            // Спроба створити замовлення без попередньої реєстрації в CarService
            carService.createOrder(unregisteredCar, unregisteredClient);
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }

        System.out.println("\nNEGATIVE 2: APPROVE EMPTY ORDER ");
        try {
            Order emptyOrder = carService.createOrder(car, client);
            emptyOrder.setStatus(StatusOrder.DIAGNOSED);
            emptyOrder.setStatus(StatusOrder.APPROVED);
        } catch (IllegalStateException exception) {
            System.out.println(exception.getMessage());
        }

        System.out.println("\nNEGATIVE 3: START WITHOUT A MECHANIC ");
        try {
            Order orderWithoutMechanic = carService.createOrder(car, client);
            orderWithoutMechanic.addTask(new Task("Inspection", new BigDecimal("20.00"), new BigDecimal("0.00")));
            orderWithoutMechanic.setStatus(StatusOrder.DIAGNOSED);
            orderWithoutMechanic.setStatus(StatusOrder.APPROVED);
            orderWithoutMechanic.setStatus(StatusOrder.IN_PROGRESS);
        } catch (IllegalStateException exception) {
            System.out.println(exception.getMessage());
        }

        System.out.println("\nNEGATIVE 4: ASSIGN MECHANIC TO TWO ACTIVE ORDERS ");
        try {
            Order firstActiveOrder = carService.createOrder(car, client);
            firstActiveOrder.addTask(new Task("Engine repair", new BigDecimal("200.00"), new BigDecimal("80.00")));
            firstActiveOrder.setStatus(StatusOrder.DIAGNOSED);
            firstActiveOrder.setStatus(StatusOrder.APPROVED);
            firstActiveOrder.setMechanic(mechanic);
            firstActiveOrder.setStatus(StatusOrder.IN_PROGRESS);

            Order secondOrder = carService.createOrder(car, client);
            secondOrder.addTask(new Task("Tire change", new BigDecimal("40.00"), new BigDecimal("0.00")));
            secondOrder.setStatus(StatusOrder.DIAGNOSED);
            secondOrder.setStatus(StatusOrder.APPROVED);
            secondOrder.setMechanic(mechanic);
            secondOrder.setStatus(StatusOrder.IN_PROGRESS);
        } catch (IllegalStateException exception) {
            System.out.println(exception.getMessage());
        }

        System.out.println("\nNEGATIVE 5: INVALID STATUS TRANSITION ");
        try {
            carService.createOrder(car, client).setStatus(StatusOrder.COMPLETED);
        } catch (IllegalStateException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
