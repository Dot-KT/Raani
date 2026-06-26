package Raani.Raani.dto;

import Raani.Raani.model.Item;
import Raani.Raani.model.Measurement;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Item response")
public record ItemResponse(
        @Schema(description = "Unique item ID") String id,
        @Schema(description = "Product name") String name,
        @Schema(description = "Product description") String description,
        @Schema(description = "Unit price") Double price,
        @Schema(description = "Whether available for purchase") Boolean available,
        @Schema(description = "Unit of measurement") Measurement measurement
) {
    public static ItemResponse from(Item item) {
        return new ItemResponse(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.getAvailable(),
                item.getMeasurement()
        );
    }
}
