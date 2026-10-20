package repository;
import org.example.domain.RepairOrder;

import java.util.Optional;

public interface RepairOrderRepository {
    void save(RepairOrder order);
    Optional<RepairOrder> findById(String id);
}
