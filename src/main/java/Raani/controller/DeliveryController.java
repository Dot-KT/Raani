package Raani.controller;

import Raani.dto.DeliveryRequest;
import Raani.dto.DeliveryResponse;
import Raani.dto.UpdateLocationRequest;
import Raani.dto.UpdateStatusRequest;
import Raani.service.DeliveryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
@RequiredArgsConstructor
@Tag(name = "Deliveries", description = "Track orders — create deliveries, update status and location, view by customer")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping
    @Operation(summary = "List all deliveries", description = "Returns every delivery/order in the system.")
    public List<DeliveryResponse> getAll() {
        return deliveryService.getAllDeliveries();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get delivery by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Delivery found"),
            @ApiResponse(responseCode = "404", description = "Delivery not found")
    })
    public DeliveryResponse getById(
            @Parameter(description = "Delivery ID") @PathVariable String id) {
        return deliveryService.getDeliveryById(id);
    }

    @GetMapping("/customer/{customerPhone}")
    @Operation(summary = "List deliveries for a customer", description = "Returns all orders placed by a specific customer phone number.")
    public List<DeliveryResponse> getByCustomerPhone(
            @Parameter(description = "Customer WhatsApp phone number") @PathVariable String customerPhone) {
        return deliveryService.getDeliveriesByCustomerPhone(customerPhone);
    }

    @PostMapping
    @Operation(summary = "Place a new order",
            description = "Creates a delivery. Only `customerPhone`, `customerName`, `itemId`, and `quantity` are required — " +
                    "item name, price, measurement, total, status, and timestamps are set automatically.")
    @ApiResponse(responseCode = "200", description = "Delivery created")
    public DeliveryResponse create(@RequestBody DeliveryRequest request) {
        return deliveryService.createDelivery(request);
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update delivery status",
            description = "Set the delivery status. Valid values: PLACED, PROCESSING, IN_TRANSIT, DELIVERED, CANCELLED.")
    public DeliveryResponse updateStatus(
            @Parameter(description = "Delivery ID") @PathVariable String id,
            @RequestBody UpdateStatusRequest request) {
        return deliveryService.updateStatus(id, request.status());
    }

    @PutMapping("/{id}/location")
    @Operation(summary = "Update delivery location",
            description = "Set the current physical location of the delivery. Updated manually by admin.")
    public DeliveryResponse updateLocation(
            @Parameter(description = "Delivery ID") @PathVariable String id,
            @RequestBody UpdateLocationRequest request) {
        return deliveryService.updateLocation(id, request.location());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a delivery")
    @ApiResponse(responseCode = "204", description = "Delivery deleted")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Delivery ID") @PathVariable String id) {
        deliveryService.deleteDelivery(id);
        return ResponseEntity.noContent().build();
    }
}
