package Raani.Raani.controller;

import Raani.Raani.model.Delivery;
import Raani.Raani.model.DeliveryStatus;
import Raani.Raani.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping
    public List<Delivery> getAll() {
        return deliveryService.getAllDeliveries();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Delivery> getById(@PathVariable String id) {
        return deliveryService.getDeliveryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/customer/{customerId}")
    public List<Delivery> getByCustomer(@PathVariable String customerId) {
        return deliveryService.getDeliveriesByCustomer(customerId);
    }

    @PostMapping
    public Delivery create(@RequestBody Delivery delivery) {
        return deliveryService.createDelivery(delivery);
    }

    @PutMapping("/{id}/status")
    public Delivery updateStatus(@PathVariable String id, @RequestBody Map<String, String> body) {
        DeliveryStatus status = DeliveryStatus.valueOf(body.get("status"));
        return deliveryService.updateStatus(id, status);
    }

    @PutMapping("/{id}/location")
    public Delivery updateLocation(@PathVariable String id, @RequestBody Map<String, String> body) {
        return deliveryService.updateLocation(id, body.get("location"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        deliveryService.deleteDelivery(id);
        return ResponseEntity.noContent().build();
    }
}
