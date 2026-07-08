package Raani.dto;

import Raani.model.DeliveryStatus;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body to update a delivery's status")
public record UpdateStatusRequest(
        @Schema(description = "New delivery status", example = "IN_TRANSIT", requiredMode = Schema.RequiredMode.REQUIRED)
        DeliveryStatus status
) {}
