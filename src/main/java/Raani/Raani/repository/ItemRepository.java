package Raani.Raani.repository;

import Raani.Raani.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ItemRepository extends MongoRepository<Item, String> {

    List<Item> findByAvailableTrue();
}
