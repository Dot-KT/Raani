package Raani.Raani.repository;

import Raani.Raani.model.Delivery;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DeliveryRepository extends MongoRepository<Delivery, String> {

    List<Delivery> findByCustomerPhone(String customerPhone);

    List<Delivery> findByOrderedAtBetween(LocalDateTime start, LocalDateTime end);
}
