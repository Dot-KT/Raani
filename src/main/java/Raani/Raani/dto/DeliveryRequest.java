package Raani.Raani.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body to place a new delivery order")
public record DeliveryRequest(
        @Schema(description = "Customer's WhatsApp phone number", example = "+2348012345678", requiredMode = Schema.RequiredMode.REQUIRED)
        String customerPhone,

        @Schema(description = "Customer's WhatsApp name", example = "Aisha", requiredMode = Schema.RequiredMode.REQUIRED)
        String customerName,

        @Schema(description = "ID of the item to order", example = "665f1a2b3c4d5e6f70890def", requiredMode = Schema.RequiredMode.REQUIRED)
        String itemId,

        @Schema(description = "Quantity to order (in the item's unit of measurement)", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
        Integer quantity
) {}
