package Raani.Raani.service;

import Raani.Raani.dto.DeliveryRequest;
import Raani.Raani.dto.DeliveryResponse;
import Raani.Raani.exception.DeliveryNotFoundException;
import Raani.Raani.model.Delivery;
import Raani.Raani.model.DeliveryStatus;
import Raani.Raani.model.Item;
import Raani.Raani.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final ItemService itemService;

    public List<DeliveryResponse> getAllDeliveries() {
        return deliveryRepository.findAll().stream().map(DeliveryResponse::from).toList();
    }

    public DeliveryResponse getDeliveryById(String id) {
        return DeliveryResponse.from(findDeliveryOrThrow(id));
    }

    public List<DeliveryResponse> getDeliveriesByCustomerPhone(String customerPhone) {
        return deliveryRepository.findByCustomerPhone(customerPhone).stream()
                .map(DeliveryResponse::from).toList();
    }

    public DeliveryResponse createDelivery(DeliveryRequest request) {
        Item item = itemService.findItemOrThrow(request.itemId());

        Delivery delivery = new Delivery();
        delivery.setCustomerPhone(request.customerPhone());
        delivery.setCustomerName(request.customerName());
        delivery.setItemId(request.itemId());
        delivery.setItemName(item.getName());
        delivery.setItemPrice(item.getPrice());
        delivery.setMeasurement(item.getMeasurement());
        delivery.setQuantity(request.quantity());
        delivery.setTotalPrice(item.getPrice() * request.quantity());
        delivery.setStatus(DeliveryStatus.PLACED);
        delivery.setOrderedAt(LocalDateTime.now());
        delivery.setUpdatedAt(LocalDateTime.now());
        return DeliveryResponse.from(deliveryRepository.save(delivery));
    }

    public DeliveryResponse updateStatus(String id, DeliveryStatus status) {
        Delivery delivery = findDeliveryOrThrow(id);
        delivery.setStatus(status);
        delivery.setUpdatedAt(LocalDateTime.now());
        return DeliveryResponse.from(deliveryRepository.save(delivery));
    }

    public DeliveryResponse updateLocation(String id, String location) {
        Delivery delivery = findDeliveryOrThrow(id);
        delivery.setCurrentLocation(location);
        delivery.setUpdatedAt(LocalDateTime.now());
        return DeliveryResponse.from(deliveryRepository.save(delivery));
    }

    public void deleteDelivery(String id) {
        findDeliveryOrThrow(id);
        deliveryRepository.deleteById(id);
    }

    private Delivery findDeliveryOrThrow(String id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new DeliveryNotFoundException(id));
    }
}
