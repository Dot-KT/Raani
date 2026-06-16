package Raani.Raani.service;

import Raani.Raani.model.Delivery;
import Raani.Raani.model.DeliveryStatus;
import Raani.Raani.model.Item;
import Raani.Raani.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final ItemService itemService;

    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public Optional<Delivery> getDeliveryById(String id) {
        return deliveryRepository.findById(id);
    }

    public List<Delivery> getDeliveriesByCustomer(String customerId) {
        return deliveryRepository.findByCustomerId(customerId);
    }

    public Delivery createDelivery(Delivery delivery) {
        // Look up item to denormalize name and price
        Item item = itemService.getItemById(delivery.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + delivery.getItemId()));

        delivery.setItemName(item.getName());
        delivery.setItemPrice(item.getPrice());
        delivery.setTotalPrice(item.getPrice() * delivery.getQuantity());
        delivery.setStatus(DeliveryStatus.PLACED);
        delivery.setOrderedAt(LocalDateTime.now());
        delivery.setUpdatedAt(LocalDateTime.now());
        return deliveryRepository.save(delivery);
    }

    public Delivery updateStatus(String id, DeliveryStatus status) {
        return deliveryRepository.findById(id)
                .map(delivery -> {
                    delivery.setStatus(status);
                    delivery.setUpdatedAt(LocalDateTime.now());
                    return deliveryRepository.save(delivery);
                })
                .orElseThrow(() -> new RuntimeException("Delivery not found with id: " + id));
    }

    public Delivery updateLocation(String id, String location) {
        return deliveryRepository.findById(id)
                .map(delivery -> {
                    delivery.setCurrentLocation(location);
                    delivery.setUpdatedAt(LocalDateTime.now());
                    return deliveryRepository.save(delivery);
                })
                .orElseThrow(() -> new RuntimeException("Delivery not found with id: " + id));
    }

    public void deleteDelivery(String id) {
        deliveryRepository.deleteById(id);
    }
}
