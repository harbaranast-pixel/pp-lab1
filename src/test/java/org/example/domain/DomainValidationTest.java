package org.example.domain;

import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class DomainValidationTest {

    @Test
    void car_WithInvalidYear_ShouldThrowException(){
        Client client = new Client("Alex", "12345");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Car("VIN1234", "Toyota", "Camry", 1800, client));
        assertEquals("Year is invalid.", exception.getMessage());
    }


    @Test
    void car_WithEmptyVin_ShouldThrowException(){
        Client client = new Client("Alex", "12345");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Car("", "Toyota", "Camry", 2020, client));
        assertEquals("VIN code cannot be empty.", exception.getMessage());

    }

    @Test
    void car_WithEmptyBrand_ShouldThrowException(){
        Client client = new Client("Alex", "12345");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Car("VIN153", "", "Camry", 2020, client));
        assertEquals("Brand cannot be blank.", exception.getMessage());

    }

    @Test
    void car_WithEmptyModel_ShouldThrowException(){
        Client client = new Client("Alex", "12345");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Car("VIN153", "Toyota", "", 2020, client));
        assertEquals("Model cannot be blank.", exception.getMessage());

    }

    @Test
    void car_WithoutOwner_ShouldThrowException(){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Car("VIN153", "Toyota", "Camry", 2020, null));
        assertEquals("Car must have an owner.", exception.getMessage());

    }

    @Test
    void task_WithNegativeTime_ShouldThrowException(){
        Task task = new Task("Inspectaion", new BigDecimal("50"), new BigDecimal("50"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> task.setTime(-5));
        assertEquals("Time cannot be negative.", exception.getMessage());
    }

    @Test
    void task_WithNegativeDetailPrice_ShouldThrowException(){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Task("Inspection",new BigDecimal("-50.00"), new BigDecimal("10.00")));


        assertEquals("Detail price cannot be null or negative.", exception.getMessage());
    }

    @Test
    void task_WithNegativeDWorkPrice_ShouldThrowException(){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Task("Inspection",new BigDecimal("50.00"), new BigDecimal("-10.00")));


        assertEquals("Work price cannot be null or negative.", exception.getMessage());
    }

    @Test
    void task_WithBlankDescription_ShouldThrowException(){

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Task("",new BigDecimal("50.00"), new BigDecimal("10.00")));


        assertEquals("Description cannot be blank.", exception.getMessage());
    }

    @Test
    void client_WithBlankName_ShouldThrowException(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Client("", "12374"));
        assertEquals("Client's name cannot be blank.", exception.getMessage());
    }

    @Test
    void client_WithBlankPhone_ShouldThrowException(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Client("Alex", ""));
        assertEquals("Client's phone cannot be blank.", exception.getMessage());
    }

    @Test
    void mechanic_WithBlankName_ShouldThrowException(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Mechanic("", "Oil change"));
        assertEquals("Mechanic's name cannot be blank.", exception.getMessage());
    }

    @Test
    void mechanic_WithBlankSpecialization_ShouldThrowException(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Mechanic("Max", ""));
        assertEquals("Mechanic's specialization cannot be blank.", exception.getMessage());
    }

    @Test
    void mechanic_StartOrder_WithAlreadyActiveOrder_ShouldThrowException(){
        Mechanic mechanic = new Mechanic("Max", "Engine");
        Client client = new Client("Alex", "122454");
        Car car = new Car("VIN1243", "Toyota", "Camry", 2020, client);


        RepairOrder order1 = new RepairOrder(car, client);
        RepairOrder order2 = new RepairOrder(car, client);

        mechanic.startOrder(order1);

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> mechanic.startOrder(order2));

        assertEquals("A mechanic cannot work on two active orders at the same time.", exception.getMessage());
        }

    @Test
    void mechanic_StartOrder_ShouldAssignOrder_WhenMechanicIsFree() {
        Mechanic mechanic = new Mechanic("Mike", "Engine");
        Client client = new Client("Alex", "12345");
        Car car = new Car("VIN123", "Toyota", "Camry", 2020, client);
        RepairOrder order = new RepairOrder(car, client);

        assertDoesNotThrow(() -> mechanic.startOrder(order));
    }
}



