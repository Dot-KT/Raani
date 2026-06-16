package Raani.Raani.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "customers")
@Schema(description = "A registered customer")
public class Customer {

    @Id
    @Schema(description = "Unique customer ID (auto-generated)", example = "665f1a2b3c4d5e6f70890abc", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "Customer's full name", example = "Amina Bello", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Indexed(unique = true)
    @Schema(description = "WhatsApp phone number (unique)", example = "2348012345678", requiredMode = Schema.RequiredMode.REQUIRED)
    private String phoneNumber;

    @Schema(description = "Delivery address", example = "12 Broad Street, Lagos")
    private String address;

    @Schema(description = "Current conversation state in the WhatsApp flow", example = "NEW", accessMode = Schema.AccessMode.READ_ONLY)
    private String conversationState;

    @Schema(description = "Registration timestamp", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime registeredAt;
}
