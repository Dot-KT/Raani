package Raani.Raani.dto;

import Raani.Raani.model.Customer;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Customer response")
public record CustomerResponse(
        @Schema(description = "Unique customer ID") String id,
        @Schema(description = "Customer's full name") String name,
        @Schema(description = "WhatsApp phone number") String phoneNumber,
        @Schema(description = "Delivery address") String address,
        @Schema(description = "Conversation state") String conversationState,
        @Schema(description = "Registration timestamp") LocalDateTime registeredAt
) {
    public static CustomerResponse from(Customer c) {
        return new CustomerResponse(
                c.getId(),
                c.getName(),
                c.getPhoneNumber(),
                c.getAddress(),
                c.getConversationState(),
                c.getRegisteredAt()
        );
    }
}
