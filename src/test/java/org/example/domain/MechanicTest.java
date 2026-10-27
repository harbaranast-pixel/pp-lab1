package org.example.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MechanicTest {

    @Test
    void constructor_WithBlankName_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Mechanic("", "Oil change"));

        assertEquals("Mechanic's name cannot be blank.", exception.getMessage());
    }

    @Test
    void constructor_WithBlankSpecialization_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Mechanic("Max", ""));

        assertEquals("Mechanic's specialization cannot be blank.", exception.getMessage());
    }

    @Test
    void startOrder_WhenAlreadyWorking_ShouldThrowException() {
        Mechanic mechanic = new Mechanic("Max", "Engine");
        Client client = new Client("Alex", "122454");
        Car car = new Car("VIN1243", "Toyota", "Camry", 2020, client);
        RepairOrder firstOrder = new RepairOrder(car, client);
        RepairOrder secondOrder = new RepairOrder(car, client);

        mechanic.startOrder(firstOrder);

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> mechanic.startOrder(secondOrder));

        assertEquals("A mechanic cannot work on two active orders at the same time.", exception.getMessage());
    }

    @Test
    void gettersAndFinishOrder_ShouldAllowStartingAnotherOrder() {
        Mechanic mechanic = new Mechanic("Mike", "Engine");
        Client client = new Client("Alex", "12345");
        Car car = new Car("VIN123", "Toyota", "Camry", 2020, client);
        RepairOrder firstOrder = new RepairOrder(car, client);
        RepairOrder secondOrder = new RepairOrder(car, client);

        assertEquals("Mike", mechanic.getName());
        assertEquals("Engine", mechanic.getSpecialization());

        mechanic.startOrder(firstOrder);
        mechanic.finishOrder();

        assertDoesNotThrow(() -> mechanic.startOrder(secondOrder));
    }
}
