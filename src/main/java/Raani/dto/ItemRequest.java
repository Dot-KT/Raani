package Raani.dto;

import Raani.model.Measurement;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body to create or update an item")
public record ItemRequest(
        @Schema(description = "Product name", example = "Premium Rice 50kg", requiredMode = Schema.RequiredMode.REQUIRED)
        String name,

        @Schema(description = "Product description", example = "High quality long-grain rice, 50kg bag")
        String description,

        @Schema(description = "Unit price", example = "45000.00", requiredMode = Schema.RequiredMode.REQUIRED)
        Double price,

        @Schema(description = "Whether the item is available for purchase", example = "true")
        Boolean available,

        @Schema(description = "Unit of measurement", example = "KG", requiredMode = Schema.RequiredMode.REQUIRED)
        Measurement measurement
) {}
