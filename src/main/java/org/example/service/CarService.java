package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.Car;
import org.example.domain.Client;
import org.example.domain.Mechanic;
import org.example.domain.RepairOrder;
import org.example.pricing.PriceCalculator;
import org.example.repository.RepairOrderRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CarService {

    private final RepairOrderRepository orderRepository;
    private final PriceCalculator priceCalculator;

    private List<Car> cars = new ArrayList<>();
    private List<Client> clients = new ArrayList<>();
    private List<Mechanic> mechanics = new ArrayList<>();

    public void registerCar(Car car) {
        if (car == null) return;
        boolean vinExists = cars.stream().anyMatch(c -> c.getVinCode().equals(car.getVinCode()));
        if (vinExists) {
            throw new IllegalArgumentException("org.example.domain.Car with this VIN is already registered in the service.");
        }
        if (!cars.contains(car)) {
            cars.add(car);
        }
    }

    public void registerClient(Client client) {
        if (client != null && !clients.contains(client)) {
            clients.add(client);
        }
    }

    public void registerMechanic(Mechanic mechanic) {
        if (mechanic != null && !mechanics.contains(mechanic)) {
            mechanics.add(mechanic);
        }
    }

    public RepairOrder createOrder(Car car, Client client) {
        if (!cars.contains(car)) {
            throw new IllegalArgumentException("Cannot create order: org.example.domain.Car is not registered in this service.");
        }
        if (!clients.contains(client)) {
            throw new IllegalArgumentException("Cannot create order: org.example.domain.Client is not registered in this service.");
        }

        RepairOrder newOrder = new RepairOrder(car, client);
        orderRepository.save(newOrder);
        return newOrder;
    }

   public RepairOrder getOrder(String id){
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order is not found:" + id));
   }

   public void updateOrder(RepairOrder order){
        orderRepository.save(order);
   }

   public void processCommercialCalculator(String orderId){
        RepairOrder order = getOrder(orderId);
        BigDecimal baseTotal = order.calculateTotalCost();
        BigDecimal finalPrice = priceCalculator.calculateCommercialPrice(baseTotal);

        System.out.println("Base cost: " + baseTotal);
        System.out.println("Commercial cost for payment: " + finalPrice);
   }
}