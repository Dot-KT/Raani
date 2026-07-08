package Raani.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body to create or update a customer")
public record CustomerRequest(
        @Schema(description = "Customer's full name", example = "Amina Bello", requiredMode = Schema.RequiredMode.REQUIRED)
        String name,

        @Schema(description = "WhatsApp phone number (unique)", example = "2348012345678", requiredMode = Schema.RequiredMode.REQUIRED)
        String phoneNumber,

        @Schema(description = "Delivery address", example = "12 Broad Street, Lagos")
        String address
) {}
