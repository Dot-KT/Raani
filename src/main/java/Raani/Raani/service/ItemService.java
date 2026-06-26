package Raani.Raani.service;

import Raani.Raani.model.Item;
import Raani.Raani.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public List<Item> getAvailableItems() {
        return itemRepository.findByAvailableTrue();
    }

    public Optional<Item> getItemById(String id) {
        return itemRepository.findById(id);
    }

    public Item createItem(Item item) {
        if (item.getAvailable() == null) {
            item.setAvailable(true);
        }
        return itemRepository.save(item);
    }

    public Item updateItem(String id, Item updated) {
        return itemRepository.findById(id)
                .map(existing -> {
                    if (updated.getName() != null) existing.setName(updated.getName());
                    if (updated.getDescription() != null) existing.setDescription(updated.getDescription());
                    if (updated.getPrice() != null) existing.setPrice(updated.getPrice());
                    if (updated.getAvailable() != null) existing.setAvailable(updated.getAvailable());
                    if (updated.getMeasurement() != null) existing.setMeasurement(updated.getMeasurement());
                    return itemRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));
    }

    public void deleteItem(String id) {
        itemRepository.deleteById(id);
    }
}
