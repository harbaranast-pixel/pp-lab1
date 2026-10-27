package org.example.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    @Test
    void constructor_WithInvalidYear_ShouldThrowException() {
        Client client = new Client("Alex", "12345");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Car("VIN1234", "Toyota", "Camry", 1800, client));

        assertEquals("Year is invalid.", exception.getMessage());
    }

    @Test
    void constructor_WithEmptyVin_ShouldThrowException() {
        Client client = new Client("Alex", "12345");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Car("", "Toyota", "Camry", 2020, client));

        assertEquals("VIN code cannot be empty.", exception.getMessage());
    }

    @Test
    void constructor_WithEmptyBrand_ShouldThrowException() {
        Client client = new Client("Alex", "12345");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Car("VIN153", "", "Camry", 2020, client));

        assertEquals("Brand cannot be blank.", exception.getMessage());
    }

    @Test
    void constructor_WithEmptyModel_ShouldThrowException() {
        Client client = new Client("Alex", "12345");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Car("VIN153", "Toyota", "", 2020, client));

        assertEquals("Model cannot be blank.", exception.getMessage());
    }

    @Test
    void constructor_WithoutOwner_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Car("VIN153", "Toyota", "Camry", 2020, null));

        assertEquals("Car must have an owner.", exception.getMessage());
    }

    @Test
    void gettersAndSetters_ShouldExposeAndUpdateCarDetails() {
        Client owner = new Client("Alex", "12345");
        Client newOwner = new Client("Jordan", "67890");
        Car car = new Car("VIN123", "Toyota", "Camry", 2020, owner);

        assertEquals("VIN123", car.getVinCode());
        assertEquals("Toyota", car.getBrand());
        assertEquals("Camry", car.getModel());
        assertEquals(2020, car.getYear());
        assertSame(owner, car.getOwner());

        car.setBrand("Honda");
        car.setModel("Civic");
        car.setYear(2021);
        car.setOwner(newOwner);

        assertEquals("Honda", car.getBrand());
        assertEquals("Civic", car.getModel());
        assertEquals(2021, car.getYear());
        assertSame(newOwner, car.getOwner());
    }
}
