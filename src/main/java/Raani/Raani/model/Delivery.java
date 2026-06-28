package Raani.Raani.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "deliveries")
@Schema(description = "A delivery/order placed by a customer")
public class Delivery {

    @Id
    @Schema(description = "Unique delivery ID (auto-generated)", example = "665f1a2b3c4d5e6f70890fed", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "Customer's WhatsApp phone number", example = "+2348012345678", requiredMode = Schema.RequiredMode.REQUIRED)
    private String customerPhone;

    @Schema(description = "Customer's WhatsApp name", example = "Aisha", requiredMode = Schema.RequiredMode.REQUIRED)
    private String customerName;

    @Schema(description = "ID of the purchased item", example = "665f1a2b3c4d5e6f70890def", requiredMode = Schema.RequiredMode.REQUIRED)
    private String itemId;

    @Schema(description = "Name of the item (auto-filled from catalog)", example = "Premium Rice 50kg", accessMode = Schema.AccessMode.READ_ONLY)
    private String itemName;

    @Schema(description = "Price of the item at time of purchase (auto-filled)", example = "45000.00", accessMode = Schema.AccessMode.READ_ONLY)
    private Double itemPrice;

    @Schema(description = "Unit of measurement (auto-filled from item)", example = "KG", accessMode = Schema.AccessMode.READ_ONLY)
    private Measurement measurement;

    @Schema(description = "Quantity ordered (in the item's unit of measurement)", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer quantity;

    @Schema(description = "Total price (itemPrice × quantity, auto-calculated)", example = "90000.00", accessMode = Schema.AccessMode.READ_ONLY)
    private Double totalPrice;

    @Schema(description = "Current delivery status (auto-set to PLACED on creation)", accessMode = Schema.AccessMode.READ_ONLY)
    private DeliveryStatus status;

    @Schema(description = "Current physical location of the delivery (updated manually by admin)", example = "Lagos Warehouse")
    private String currentLocation;

    @Schema(description = "Order placement timestamp", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime orderedAt;

    @Schema(description = "Last update timestamp", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;
}
