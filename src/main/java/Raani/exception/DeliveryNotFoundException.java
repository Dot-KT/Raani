package Raani.exception;

public class DeliveryNotFoundException extends RuntimeException {

    public DeliveryNotFoundException(String id) {
        super("Delivery with ID '" + id + "' does not exist.");
    }
}
