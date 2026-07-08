package Raani.service;

import Raani.dto.CustomerRequest;
import Raani.dto.CustomerResponse;
import Raani.exception.CustomerNotFoundException;
import Raani.model.Customer;
import Raani.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream().map(CustomerResponse::from).toList();
    }

    public CustomerResponse getCustomerById(String id) {
        return CustomerResponse.from(findCustomerOrThrow(id));
    }

    public CustomerResponse getCustomerByPhone(String phoneNumber) {
        return CustomerResponse.from(customerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> CustomerNotFoundException.byPhone(phoneNumber)));
    }

    public CustomerResponse createCustomer(CustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setPhoneNumber(request.phoneNumber());
        customer.setAddress(request.address());
        customer.setConversationState("NEW");
        customer.setRegisteredAt(LocalDateTime.now());
        return CustomerResponse.from(customerRepository.save(customer));
    }

    public CustomerResponse updateCustomer(String id, CustomerRequest request) {
        Customer existing = findCustomerOrThrow(id);
        if (request.name() != null) existing.setName(request.name());
        if (request.phoneNumber() != null) existing.setPhoneNumber(request.phoneNumber());
        if (request.address() != null) existing.setAddress(request.address());
        return CustomerResponse.from(customerRepository.save(existing));
    }

    public void deleteCustomer(String id) {
        findCustomerOrThrow(id);
        customerRepository.deleteById(id);
    }

    private Customer findCustomerOrThrow(String id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> CustomerNotFoundException.byId(id));
    }
}
