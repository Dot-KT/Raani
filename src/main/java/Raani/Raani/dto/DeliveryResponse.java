package Raani.Raani.dto;

import Raani.Raani.model.Delivery;
import Raani.Raani.model.DeliveryStatus;
import Raani.Raani.model.Measurement;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Delivery response")
public record DeliveryResponse(
        @Schema(description = "Unique delivery ID") String id,
        @Schema(description = "Customer's WhatsApp phone number") String customerPhone,
        @Schema(description = "Customer's WhatsApp name") String customerName,
        @Schema(description = "Item ID") String itemId,
        @Schema(description = "Item name") String itemName,
        @Schema(description = "Item price at time of purchase") Double itemPrice,
        @Schema(description = "Unit of measurement") Measurement measurement,
        @Schema(description = "Quantity ordered") Integer quantity,
        @Schema(description = "Total price") Double totalPrice,
        @Schema(description = "Delivery status") DeliveryStatus status,
        @Schema(description = "Current location") String currentLocation,
        @Schema(description = "Order placement time") LocalDateTime orderedAt,
        @Schema(description = "Last update time") LocalDateTime updatedAt
) {
    public static DeliveryResponse from(Delivery d) {
        return new DeliveryResponse(
                d.getId(),
                d.getCustomerPhone(),
                d.getCustomerName(),
                d.getItemId(),
                d.getItemName(),
                d.getItemPrice(),
                d.getMeasurement(),
                d.getQuantity(),
                d.getTotalPrice(),
                d.getStatus(),
                d.getCurrentLocation(),
                d.getOrderedAt(),
                d.getUpdatedAt()
        );
    }
}
