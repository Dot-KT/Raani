package Raani.exception;

public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(String id) {
        super("Item with ID '" + id + "' does not exist in the catalog.");
    }
}
