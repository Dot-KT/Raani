package Raani.Raani.service;

import Raani.Raani.dto.ItemRequest;
import Raani.Raani.dto.ItemResponse;
import Raani.Raani.exception.ItemNotFoundException;
import Raani.Raani.model.Item;
import Raani.Raani.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<ItemResponse> getAllItems() {
        return itemRepository.findAll().stream().map(ItemResponse::from).toList();
    }

    public List<ItemResponse> getAvailableItems() {
        return itemRepository.findByAvailableTrue().stream().map(ItemResponse::from).toList();
    }

    public ItemResponse getItemById(String id) {
        return ItemResponse.from(findItemOrThrow(id));
    }

    public ItemResponse createItem(ItemRequest request) {
        Item item = new Item();
        item.setName(request.name());
        item.setDescription(request.description());
        item.setPrice(request.price());
        item.setAvailable(request.available() != null ? request.available() : true);
        item.setMeasurement(request.measurement());
        return ItemResponse.from(itemRepository.save(item));
    }

    public ItemResponse updateItem(String id, ItemRequest request) {
        Item existing = findItemOrThrow(id);
        if (request.name() != null) existing.setName(request.name());
        if (request.description() != null) existing.setDescription(request.description());
        if (request.price() != null) existing.setPrice(request.price());
        if (request.available() != null) existing.setAvailable(request.available());
        if (request.measurement() != null) existing.setMeasurement(request.measurement());
        return ItemResponse.from(itemRepository.save(existing));
    }

    public void deleteItem(String id) {
        findItemOrThrow(id);
        itemRepository.deleteById(id);
    }

    public Item findItemOrThrow(String id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
    }
}
