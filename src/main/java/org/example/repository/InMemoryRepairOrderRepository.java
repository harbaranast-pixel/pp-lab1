package org.example.repository;

import org.example.domain.RepairOrder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryRepairOrderRepository implements RepairOrderRepository{
    private final Map<String, RepairOrder> storage = new HashMap<>();

    @Override
    public void save(RepairOrder order) {
        storage.put(order.getId(), order);
    }

    @Override
    public Optional<RepairOrder> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }
}
