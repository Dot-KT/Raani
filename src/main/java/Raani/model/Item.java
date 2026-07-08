package Raani.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "items")
@Schema(description = "A product available for purchase")
public class Item {

    @Id
    @Schema(description = "Unique item ID (auto-generated)", example = "665f1a2b3c4d5e6f70890def", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "Product name", example = "Premium Rice 50kg", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Product description", example = "High quality long-grain rice, 50kg bag")
    private String description;

    @Schema(description = "Unit price", example = "45000.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double price;

    @Schema(description = "Whether the item is currently available for purchase (defaults to true)", example = "true")
    private Boolean available;

    @Schema(description = "Unit of measurement for this item", example = "KG", requiredMode = Schema.RequiredMode.REQUIRED)
    private Measurement measurement;
}
