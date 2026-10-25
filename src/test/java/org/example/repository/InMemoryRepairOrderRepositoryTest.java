package org.example.repository;

import org.example.domain.Car;
import org.example.domain.Client;
import org.example.domain.RepairOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryRepairOrderRepositoryTest {
    private InMemoryRepairOrderRepository repository;
    private RepairOrder order;

    @BeforeEach
    void setUp() {
        repository = new InMemoryRepairOrderRepository();
        Client client = new Client("Alex", "12345");
        Car car = new Car("VIN123", "Toyota", "Camry", 2020, client);
        order = new RepairOrder(car, client);
    }

    @Test
    void saveAndFindById_ShouldReturnSavedOrder() {
        repository.save(order);
        Optional<RepairOrder> retrievedOrder = repository.findById(order.getId());

        assertTrue(retrievedOrder.isPresent());
        assertEquals(order.getId(), retrievedOrder.get().getId());
    }

    @Test
    void findById_ShouldReturnEmpty_WhenOrderDoesNotExist() {
        Optional<RepairOrder> retrievedOrder = repository.findById("invalid-id");

        assertTrue(retrievedOrder.isEmpty());
    }
}
