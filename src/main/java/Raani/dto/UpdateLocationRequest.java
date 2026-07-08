package Raani.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body to update a delivery's current location")
public record UpdateLocationRequest(
        @Schema(description = "Current location of the delivery", example = "Lagos Warehouse", requiredMode = Schema.RequiredMode.REQUIRED)
        String location
) {}
