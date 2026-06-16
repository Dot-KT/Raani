package Raani.Raani.controller;

import Raani.Raani.model.Item;
import Raani.Raani.service.ItemService;
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
    public List<Item> getAll() {
        return itemService.getAllItems();
    }

    @GetMapping("/available")
    @Operation(summary = "List available items", description = "Returns only items where `available` is true. This is what customers see on WhatsApp.")
    public List<Item> getAvailable() {
        return itemService.getAvailableItems();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get item by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Item found"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    public ResponseEntity<Item> getById(
            @Parameter(description = "Item ID") @PathVariable String id) {
        return itemService.getItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Add a new item", description = "Creates a new catalog item. `available` defaults to true if not provided.")
    @ApiResponse(responseCode = "200", description = "Item created")
    public Item create(@RequestBody Item item) {
        return itemService.createItem(item);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an item", description = "Partial update — only non-null fields are applied.")
    public Item update(
            @Parameter(description = "Item ID") @PathVariable String id,
            @RequestBody Item item) {
        return itemService.updateItem(id, item);
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
