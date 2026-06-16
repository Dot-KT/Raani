package Raani.Raani.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "customers")
public class Customer {

    @Id
    private String id;

    private String name;

    @Indexed(unique = true)
    private String phoneNumber;

    private String address;

    private String conversationState;

    private LocalDateTime registeredAt;
}
