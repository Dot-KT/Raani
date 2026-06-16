package Raani.Raani.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request body to update a delivery's current location")
public class UpdateLocationRequest {

    @Schema(description = "Current location of the delivery", example = "Lagos Warehouse", requiredMode = Schema.RequiredMode.REQUIRED)
    private String location;
}
