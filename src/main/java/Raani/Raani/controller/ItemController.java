package Raani.Raani.controller;

import Raani.Raani.model.Item;
import Raani.Raani.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<Item> getAll() {
        return itemService.getAllItems();
    }

    @GetMapping("/available")
    public List<Item> getAvailable() {
        return itemService.getAvailableItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getById(@PathVariable String id) {
        return itemService.getItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Item create(@RequestBody Item item) {
        return itemService.createItem(item);
    }

    @PutMapping("/{id}")
    public Item update(@PathVariable String id, @RequestBody Item item) {
        return itemService.updateItem(id, item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
