package Raani.controller;

import Raani.dto.ItemRequest;
import Raani.dto.ItemResponse;
import Raani.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@Tag(name = "Items", description = "Manage the product catalog — add, view, update, and remove items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    @Operation(summary = "List all items", description = "Returns every item in the catalog, including unavailable ones.")
    public List<ItemResponse> getAll() {
        return itemService.getAllItems();
    }

    @GetMapping("/available")
    @Operation(summary = "List available items", description = "Returns only items where `available` is true. This is what customers see on WhatsApp.")
    public List<ItemResponse> getAvailable() {
        return itemService.getAvailableItems();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get item by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Item found"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    public ItemResponse getById(
            @Parameter(description = "Item ID") @PathVariable String id) {
        return itemService.getItemById(id);
    }

    @PostMapping
    @Operation(summary = "Add a new item", description = "Creates a new catalog item. `available` defaults to true if not provided.")
    @ApiResponse(responseCode = "200", description = "Item created")
    public ItemResponse create(@RequestBody ItemRequest request) {
        return itemService.createItem(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an item", description = "Partial update — only non-null fields are applied.")
    public ItemResponse update(
            @Parameter(description = "Item ID") @PathVariable String id,
            @RequestBody ItemRequest request) {
        return itemService.updateItem(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an item")
    @ApiResponse(responseCode = "204", description = "Item deleted")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Item ID") @PathVariable String id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
