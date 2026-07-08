package Raani.controller;

import Raani.dto.CustomerRequest;
import Raani.dto.CustomerResponse;
import Raani.service.CustomerService;
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
    public List<CustomerResponse> getAll() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Customer found"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    public CustomerResponse getById(
            @Parameter(description = "Customer ID", example = "665f1a2b3c4d5e6f70890abc")
            @PathVariable String id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping("/phone/{phoneNumber}")
    @Operation(summary = "Look up customer by phone number", responses = {
            @ApiResponse(responseCode = "200", description = "Customer found"),
            @ApiResponse(responseCode = "404", description = "No customer with that phone number")
    })
    public CustomerResponse getByPhone(
            @Parameter(description = "WhatsApp phone number", example = "2348012345678")
            @PathVariable String phoneNumber) {
        return customerService.getCustomerByPhone(phoneNumber);
    }

    @PostMapping
    @Operation(summary = "Register a new customer",
            description = "Creates a new customer. `registeredAt` and `conversationState` are set automatically.")
    @ApiResponse(responseCode = "200", description = "Customer created")
    public CustomerResponse create(@RequestBody CustomerRequest request) {
        return customerService.createCustomer(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a customer", description = "Partial update — only non-null fields are applied.")
    public CustomerResponse update(
            @Parameter(description = "Customer ID") @PathVariable String id,
            @RequestBody CustomerRequest request) {
        return customerService.updateCustomer(id, request);
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
