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

    @Test
    void updateOrder_ShouldCallRepositorySave(){

        RepairOrder order = new RepairOrder(car, client);

        carService.updateOrder(order);


        verify(orderRepositoryMock).save(order);
    }

    @Test
    void createOrder_WithUnregisteredCar_ShouldThrowException(){
        Car unregisteredCar = new Car("VIN123", "Toyota", "Camry", 2020, client);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> carService.createOrder(unregisteredCar, client));

        assertEquals("Cannot create order: Car is not registered in this service.", exception.getMessage());

    }

    @Test
    void createOrder_WithUnregisteredClient_ShouldThrowException(){
        Client unregisteredClient = new Client("Max", "713632");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> carService.createOrder(car, unregisteredClient));

        assertEquals("Cannot create order: Client is not registered in this service.", exception.getMessage());

    }

    @Test
    void registerCar_WithAlreadyRegisteredVIN_ShouldThrowException(){
        Car car1 = new Car("VIN2436", "Toyota", "Camry", 2020, client);
        Car car2 = new Car("VIN2436", "BMW", "X5", 2020, client);

        carService.registerCar(car1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> carService.registerCar(car2));

        assertEquals("Car with this VIN is already registered in the service.", exception.getMessage());

    }
}
