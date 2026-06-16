package Raani.Raani.service;

import Raani.Raani.model.Customer;
import Raani.Raani.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(String id) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> getCustomerByPhone(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber);
    }

    public Customer createCustomer(Customer customer) {
        customer.setRegisteredAt(LocalDateTime.now());
        customer.setConversationState("NEW");
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(String id, Customer updated) {
        return customerRepository.findById(id)
                .map(existing -> {
                    if (updated.getName() != null) existing.setName(updated.getName());
                    if (updated.getPhoneNumber() != null) existing.setPhoneNumber(updated.getPhoneNumber());
                    if (updated.getAddress() != null) existing.setAddress(updated.getAddress());
                    if (updated.getConversationState() != null) existing.setConversationState(updated.getConversationState());
                    return customerRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }

    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }
}
