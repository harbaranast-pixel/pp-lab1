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
    void calcelOrder_ShouldChangeStatusToCancelled(){

        assertDoesNotThrow(() -> order.cancel());

        assertEquals(StatusOrder.CANCELLED, order.getStatus());
    }

    @Test
    void cancelOrder_ShouldThrowExceprion_WhenAlreadyCompleted(){

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
}
