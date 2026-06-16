package Raani.Raani.controller;

import Raani.Raani.model.Customer;
import Raani.Raani.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Customers", description = "Manage registered customers — create, view, update, and delete customer records")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    @Operation(summary = "List all customers", description = "Returns every registered customer.")
    public List<Customer> getAll() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Customer found"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    public ResponseEntity<Customer> getById(
            @Parameter(description = "Customer ID", example = "665f1a2b3c4d5e6f70890abc")
            @PathVariable String id) {
        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/phone/{phoneNumber}")
    @Operation(summary = "Look up customer by phone number", responses = {
            @ApiResponse(responseCode = "200", description = "Customer found"),
            @ApiResponse(responseCode = "404", description = "No customer with that phone number")
    })
    public ResponseEntity<Customer> getByPhone(
            @Parameter(description = "WhatsApp phone number", example = "2348012345678")
            @PathVariable String phoneNumber) {
        return customerService.getCustomerByPhone(phoneNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Register a new customer",
            description = "Creates a new customer. `registeredAt` and `conversationState` are set automatically.")
    @ApiResponse(responseCode = "200", description = "Customer created")
    public Customer create(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a customer", description = "Partial update — only non-null fields are applied.")
    public Customer update(
            @Parameter(description = "Customer ID") @PathVariable String id,
            @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a customer")
    @ApiResponse(responseCode = "204", description = "Customer deleted")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Customer ID") @PathVariable String id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
