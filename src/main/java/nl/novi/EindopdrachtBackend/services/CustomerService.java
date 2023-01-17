package nl.novi.EindopdrachtBackend.services;

import nl.novi.EindopdrachtBackend.dtos.CustomerDto;
import nl.novi.EindopdrachtBackend.exceptions.DuplicateRecordException;
import nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException;
import nl.novi.EindopdrachtBackend.models.Customer;
import nl.novi.EindopdrachtBackend.models.HearingAid;
import nl.novi.EindopdrachtBackend.repositories.CustomerRepository;
import nl.novi.EindopdrachtBackend.repositories.EarPieceRepository;
import nl.novi.EindopdrachtBackend.repositories.HearingAidRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final HearingAidRepository hearingAidRepository;

    public CustomerService(
            CustomerRepository customerRepository,
            HearingAidRepository hearingAidRepository) {

        this.customerRepository = customerRepository;
        this.hearingAidRepository = hearingAidRepository;
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

    public ResponseEntity<Object> addHearingAid(Long customerId, String productcode) {
        if (customerRepository.findById(customerId).isEmpty())
            throw new IndexOutOfBoundsException(String.format("Customer with id %d was not found", customerId));

        Customer customer = customerRepository.findById(customerId).get();

        if (hearingAidRepository.findById(productcode).isEmpty())
            throw new IndexOutOfBoundsException(String.format("Hearing aid with id %s was not found", productcode));

        HearingAid hearingAid = hearingAidRepository.findById(productcode).get();

        customer.addHearingAid(hearingAid);
        customerRepository.save(customer);

        return ResponseEntity.ok("Hearing aid added to customer");
    }

    public ResponseEntity<Object> removeHearingAid(Long customerId, String productcode) {
        if (customerRepository.findById(customerId).isEmpty())
            throw new IndexOutOfBoundsException(String.format("Customer with id %d was not found", customerId));

        Customer customer = customerRepository.findById(customerId).get();

        if (hearingAidRepository.findById(productcode).isEmpty())
            throw new IndexOutOfBoundsException(String.format("Earpiece with id %s was not found", productcode));

        HearingAid hearingAidToRemove = customer.getHearingAidList().stream().filter((a) -> a.getProductcode().equals(productcode)).findAny().get();

        customer.removeHearingAid(hearingAidToRemove);
        customerRepository.save(customer);

        return ResponseEntity.ok("Hearing aid removed from customer");
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
