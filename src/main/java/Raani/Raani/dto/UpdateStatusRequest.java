package Raani.Raani.dto;

import Raani.Raani.model.DeliveryStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request body to update a delivery's status")
public class UpdateStatusRequest {

    @Schema(description = "New delivery status", example = "IN_TRANSIT", requiredMode = Schema.RequiredMode.REQUIRED)
    private DeliveryStatus status;
}
