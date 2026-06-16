package Raani.Raani.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "deliveries")
public class Delivery {

    @Id
    private String id;

    private String customerId;

    private String itemId;

    private String itemName;

    private Double itemPrice;

    private Integer quantity;

    private Double totalPrice;

    private DeliveryStatus status;

    private String currentLocation;

    private LocalDateTime orderedAt;

    private LocalDateTime updatedAt;
}
