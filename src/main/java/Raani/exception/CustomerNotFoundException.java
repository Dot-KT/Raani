package Raani.exception;

public class CustomerNotFoundException extends RuntimeException {

    private CustomerNotFoundException(String message) {
        super(message);
    }

    public static CustomerNotFoundException byId(String id) {
        return new CustomerNotFoundException("Customer with ID '" + id + "' does not exist.");
    }

    public static CustomerNotFoundException byPhone(String phoneNumber) {
        return new CustomerNotFoundException("No customer found with phone number '" + phoneNumber + "'.");
    }
}
