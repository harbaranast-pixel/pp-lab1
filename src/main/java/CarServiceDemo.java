import java.math.BigDecimal;

public class CarServiceDemo {
    public static void main(String[] args) {
        System.out.println("POSITIVE SCENARIO: ");

        CarService carService = new CarService();

        Client client = new Client("Alex Morgan", "555-0100");
        carService.registerClient(client);

        Car car = new Car("DEMO-CAR-001", "Toyota", "Corolla", 2022, client);
        carService.registerCar(car);

        Mechanic mechanic = new Mechanic("Jordan Lee", "Motor");
        carService.registerMechanic(mechanic);

        Order completedOrder = carService.createOrder(car, client);

        Task oilChange = new Task("Oil change", new BigDecimal("35.50"), new BigDecimal("10.00"));
        Task brakeService = new Task("Brake service", new BigDecimal("120.00"), new BigDecimal("45.75"));

        completedOrder.addTask(oilChange);
        completedOrder.addTask(brakeService);

        completedOrder.diagnose();
        completedOrder.approve();
        completedOrder.setMechanic(mechanic);
        completedOrder.startWork();

        oilChange.completeTask();
        brakeService.completeTask();

        completedOrder.complete();

        System.out.println("Total cost: " + completedOrder.calculateTotalCost());


        System.out.println("\nNEGATIVE 1: COORDINATOR BLOCKS UNREGISTERED CAR ");
        try {
            Client unregisteredClient = new Client("Ghost", "000");
            Car unregisteredCar = new Car("GHOST-VIN", "BMW", "X5", 2020, unregisteredClient);
            carService.createOrder(unregisteredCar, unregisteredClient);
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }

        System.out.println("\nNEGATIVE 2: APPROVE EMPTY ORDER ");
        try {
            Order emptyOrder = carService.createOrder(car, client);
            emptyOrder.diagnose();
            emptyOrder.approve();
        } catch (IllegalStateException exception) {
            System.out.println(exception.getMessage());
        }

        System.out.println("\nNEGATIVE 3: START WITHOUT A MECHANIC ");
        try {
            Order orderWithoutMechanic = carService.createOrder(car, client);
            orderWithoutMechanic.addTask(new Task("Inspection", new BigDecimal("20.00"), new BigDecimal("0.00")));
            orderWithoutMechanic.diagnose();
            orderWithoutMechanic.approve();
            orderWithoutMechanic.startWork();
        } catch (IllegalStateException exception) {
            System.out.println(exception.getMessage());
        }

        System.out.println("\nNEGATIVE 4: ASSIGN MECHANIC TO TWO ACTIVE ORDERS ");
        try {
            Order firstActiveOrder = carService.createOrder(car, client);
            firstActiveOrder.addTask(new Task("Engine repair", new BigDecimal("200.00"), new BigDecimal("80.00")));
            firstActiveOrder.diagnose();
            firstActiveOrder.approve();
            firstActiveOrder.setMechanic(mechanic);
            firstActiveOrder.startWork();

            Order secondOrder = carService.createOrder(car, client);
            secondOrder.addTask(new Task("Tire change", new BigDecimal("40.00"), new BigDecimal("0.00")));
            secondOrder.diagnose();
            secondOrder.approve();
            secondOrder.setMechanic(mechanic);
            secondOrder.startWork();
        } catch (IllegalStateException exception) {
            System.out.println(exception.getMessage());
        }

        System.out.println("\nNEGATIVE 5: INVALID STATUS TRANSITION ");
        try {
            Order freshOrder = carService.createOrder(car, client);
            freshOrder.complete();
        } catch (IllegalStateException exception) {
            System.out.println(exception.getMessage());
        }

        System.out.println("\nNEGATIVE 6: MODIFY COMPLETED ORDER ");
        try {
            Task extraTask = new Task("Extra polishing", new BigDecimal("0.00"), new BigDecimal("50.00"));
            completedOrder.addTask(extraTask);
        } catch (IllegalStateException exception) {
            System.out.println(exception.getMessage());
        }
    }
}