package org.example.service;

import org.example.domain.*;
import org.example.pricing.PriceCalculator;
import org.example.repository.RepairOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class CarServiceTest {

    private RepairOrderRepository orderRepositoryMock;
    private PriceCalculator priceCalculatorMock;

    private CarService carService;
    private Client client;
    private Car car;

    @BeforeEach
    void setUp(){

        orderRepositoryMock = mock(RepairOrderRepository.class);
        priceCalculatorMock = mock(PriceCalculator.class);

        carService = new CarService(orderRepositoryMock, priceCalculatorMock);

        client = new Client("Alex", "1372");
        car = new Car("VIN123", "Toyota", "Camry", 2020, client);

        carService.registerClient(client);
        carService.registerCar(car);

    }

    @Test
    void createOrder_ShouldSaveOrderToRepository(){

        RepairOrder newOrder= carService.createOrder(car,client);


        verify(orderRepositoryMock).save(newOrder);
        assertNotNull(newOrder.getId());
    }

    @Test
    void processCommercialCalculator_ShouldUseCalculatorAndPrintOut(){

        RepairOrder order = new RepairOrder(car,client);
        order.addTask(new Task("Test", new BigDecimal("100.00"), new BigDecimal("50.00")));

        when(orderRepositoryMock.findById(order.getId())).thenReturn(Optional.of(order));

        when(priceCalculatorMock.calculateCommercialPrice(any(BigDecimal.class))).thenReturn(new BigDecimal("500.00"));

        assertDoesNotThrow(() -> carService.processCommercialCalculator(order.getId()));

        verify(priceCalculatorMock).calculateCommercialPrice(new BigDecimal("150.00"));
    }
}
