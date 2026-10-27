package org.example.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class RepairOrderTest {
    private RepairOrder order;
    private Car car;
    private  Client client;
    private  Mechanic mechanic;

    @BeforeEach
    void setUp() {
        client = new Client("Alex", "12345");
        car = new Car("VIN123", "Toyota", "Camry", 2020, client);
        mechanic = new Mechanic("Jordan Lee", "Motor");
        order = new RepairOrder(car, client);
    }

    @Test
    void calculateTotalCost_ShouldReturnCorrectSumOfAllTasks(){
        Task oilChange = new Task("Oil change", new BigDecimal("35.50"), new BigDecimal("10.00"));
        Task brakeService = new Task("Brake service", new BigDecimal("120.00"), new BigDecimal("45.75"));
        order.addTask(oilChange);
        order.addTask(brakeService);

        BigDecimal total = order.calculateTotalCost();


        assertEquals(new BigDecimal("211.25"), total);

    }


    @Test
    void validStateTransition_ShouldReachCompleteStatus(){
        Task task = new Task("Inspection", new BigDecimal("0.00"), new BigDecimal("50.00"));
        order.addTask(task);


        assertDoesNotThrow(() -> order.diagnose());
        assertEquals(StatusOrder.DIAGNOSED, order.getStatus());


        assertDoesNotThrow(() -> order.approve());
        assertEquals(StatusOrder.APPROVED, order.getStatus());


        order.setMechanic(mechanic);
        assertDoesNotThrow(() -> order.startWork());
        assertEquals(StatusOrder.IN_PROGRESS, order.getStatus());

        task.completeTask();

        assertDoesNotThrow(() -> order.complete());
        assertEquals(StatusOrder.COMPLETED, order.getStatus());
        assertDoesNotThrow(() -> mechanic.startOrder(new RepairOrder(car, client)));
    }

    @Test
    void approveOrderWithoutTasks_ShouldThrowException(){
        order.diagnose();

        IllegalStateException exception = assertThrows(IllegalStateException.class, order::approve);
        assertEquals("An order must have at least one task before it can be approved", exception.getMessage());
    }

    @Test
    void completeOrderWithUncompletedTask_ShouldThrowException(){
        Task task = new Task("Repair", new BigDecimal("100"), new BigDecimal("100"));
        order.addTask(task);
        order.diagnose();
        order.approve();
        order.setMechanic(mechanic);
        order.startWork();

        IllegalStateException exception = assertThrows(IllegalStateException.class, order::complete);
        assertEquals("All tasks must be completed before the order can be completed", exception.getMessage());
    }

    @Test
    void calculateTotalCost_ShouldReturnZero_WhenNoTaskAdded(){


        BigDecimal total = order.calculateTotalCost();

        assertEquals(0, BigDecimal.ZERO.compareTo((total)));
    }

    @Test
    void cancelOrder_ShouldChangeStatusToCancelled(){

        assertDoesNotThrow(() -> order.cancel());

        assertEquals(StatusOrder.CANCELLED, order.getStatus());
    }

    @Test
    void cancelOrder_ShouldThrowException_WhenAlreadyCompleted(){

        Task task = new Task("Test", BigDecimal.ZERO, BigDecimal.TEN);
        order.addTask(task);
        order.diagnose();
        order.approve();
        order.setMechanic(mechanic);
        order.startWork();
        task.completeTask();
        order.complete();



        IllegalStateException exception = assertThrows(IllegalStateException.class, order::cancel);
        assertEquals("Cannot cancel an already completed or cancelled order.", exception.getMessage());
    }

    @Test
    void addTask_WithNullTask_ShouldThrowException(){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> order.addTask(null));

        assertEquals("Cannot add a null task.", exception.getMessage());
    }

    @Test
    void completeOrder_WhenNotInProgress_ShouldThrowException(){

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> order.complete());

        assertEquals("Only in-progress orders can be completed.", exception.getMessage());
    }

    @Test
    void gettersAndSetters_ShouldExposeAndUpdateOrderDetails() {
        Client newClient = new Client("Taylor", "67890");
        assertSame(car, order.getCar());
        assertSame(client, order.getClient());
        assertNull(order.getMechanic());
        assertEquals(StatusOrder.CREATED, order.getStatus());

        order.setClient(newClient);
        order.setMechanic(mechanic);

        assertSame(newClient, order.getClient());
        assertSame(mechanic, order.getMechanic());
    }

    @Test
    void getTasks_ShouldReturnDefensiveCopy() {
        Task task = new Task("Inspection", BigDecimal.ZERO, BigDecimal.TEN);
        order.addTask(task);

        order.getTasks().clear();

        assertEquals(1, order.getTasks().size());
        assertSame(task, order.getTasks().get(0));
    }

    @Test
    void setMechanic_WhenOrderIsInProgress_ShouldReleasePreviousMechanic() {
        Mechanic replacement = new Mechanic("Taylor", "Brakes");
        order.addTask(new Task("Repair", BigDecimal.ZERO, BigDecimal.TEN));
        order.diagnose();
        order.approve();
        order.setMechanic(mechanic);
        order.startWork();

        order.setMechanic(replacement);

        assertSame(replacement, order.getMechanic());
        assertDoesNotThrow(() -> mechanic.startOrder(new RepairOrder(car, client)));
        assertThrows(IllegalStateException.class, () -> replacement.startOrder(new RepairOrder(car, client)));
    }
}
