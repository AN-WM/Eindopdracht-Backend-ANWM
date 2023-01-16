package nl.novi.EindopdrachtBackend.services;

import nl.novi.EindopdrachtBackend.dtos.CustomerDto;
import nl.novi.EindopdrachtBackend.exceptions.DuplicateRecordException;
import nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException;
import nl.novi.EindopdrachtBackend.models.Customer;
import nl.novi.EindopdrachtBackend.repositories.CustomerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerDto> getAllCustomers() {
        List<CustomerDto> collection = new ArrayList<>();
        List<Customer> list = customerRepository.findAll();
        for (Customer customer : list) {
            collection.add(fromCustomer(customer));
        }
        return collection;
    }

    public CustomerDto getCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty())
            throw new IndexOutOfBoundsException(String.format("ID %d was not found", id));

        return fromCustomer(customer.get());
    }

    public Long saveCustomer(CustomerDto customerDto) {
        Long id = customerDto.getId();
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isPresent())
            throw new DuplicateRecordException(String.format("A customer with id %s already exists", id));

        Customer savedCustomer = customerRepository.save(toCustomer(customerDto));
        return savedCustomer.getId();
    }

    public CustomerDto updateCustomer(CustomerDto customerDto) {
        Long id = customerDto.getId();
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty())
            throw new IndexOutOfBoundsException(String.format("Customer with id %d was not found", id));

        customerRepository.save(toCustomer(customerDto));
        return customerDto;
    }

    public ResponseEntity<Object> deleteCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty())
            throw new IndexOutOfBoundsException(String.format("Customer with id %d was not found", id));

        customerRepository.deleteById(id);
        return ResponseEntity.ok("Customer removed from database");
    }

    public Customer toCustomer(CustomerDto dto) {
        var customer = new Customer();

        customer.setId(dto.getId());
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setAddress(dto.getAddress());
        customer.setZipCode(dto.getZipCode());
        customer.setCity(dto.getCity());
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setEmail(dto.getEmail());

        return customer;
    }

    // Dit is de vertaalmethode van Customer naar CustomerDto
    public CustomerDto fromCustomer(Customer customer) {
        CustomerDto dto = new CustomerDto();

        dto.setId(customer.getId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setAddress(customer.getAddress());
        dto.setZipCode(customer.getZipCode());
        dto.setCity(customer.getCity());
        dto.setPhoneNumber(customer.getPhoneNumber());
        dto.setEmail(customer.getEmail());

        return dto;
    }
}

