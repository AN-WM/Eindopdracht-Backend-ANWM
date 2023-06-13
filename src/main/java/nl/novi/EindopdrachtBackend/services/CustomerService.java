package nl.novi.EindopdrachtBackend.services;

import nl.novi.EindopdrachtBackend.dtos.CustomerDto;
import nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException;
import nl.novi.EindopdrachtBackend.models.Customer;
import nl.novi.EindopdrachtBackend.models.Document;
import nl.novi.EindopdrachtBackend.models.HearingAid;
import nl.novi.EindopdrachtBackend.models.Receipt;
import nl.novi.EindopdrachtBackend.repositories.CustomerRepository;
import nl.novi.EindopdrachtBackend.repositories.DocumentRepository;
import nl.novi.EindopdrachtBackend.repositories.HearingAidRepository;
import nl.novi.EindopdrachtBackend.repositories.ReceiptRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final HearingAidRepository hearingAidRepository;
    private final ReceiptRepository receiptRepository;
    private final DocumentRepository documentRepository;

    public CustomerService(CustomerRepository customerRepository,
                           HearingAidRepository hearingAidRepository,
                           ReceiptRepository receiptRepository,
                           DocumentRepository documentRepository) {
        this.customerRepository = customerRepository;
        this.hearingAidRepository = hearingAidRepository;
        this.receiptRepository = receiptRepository;
        this.documentRepository = documentRepository;
    }

    public ResponseEntity<CustomerDto> createCustomer(CustomerDto customerDto) {
        Customer savedCustomer = customerRepository.save(toCustomer(customerDto));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{customerId}")
                .buildAndExpand(savedCustomer.getId()).toUri();

        return ResponseEntity.created(location).body(fromCustomer(savedCustomer));
    }

    public ResponseEntity<CustomerDto> getCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty())
            throw new IndexOutOfBoundsException(String.format("ID %d was not found", id));

        return ResponseEntity.ok(fromCustomer(customer.get()));
    }

    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        List<CustomerDto> collection = new ArrayList<>();
        List<Customer> list = customerRepository.findAll();
        for (Customer customer : list) {
            collection.add(fromCustomer(customer));
        }
        return ResponseEntity.ok().body(collection);
    }

    public ResponseEntity<CustomerDto> updateCustomer(Long customerId, CustomerDto customerDto) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty())
            throw new IndexOutOfBoundsException(String.format("Customer with id %d was not found", customerId));

        customerRepository.save(toCustomer(customerDto));

        return ResponseEntity.ok(customerDto);
    }

    public ResponseEntity<Object> deleteCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty())
            throw new IndexOutOfBoundsException(String.format("Customer with id %d was not found", id));

        customerRepository.deleteById(id);
        return ResponseEntity.ok(String.format("Customer with ID %s was removed from the database", id));
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

    public ResponseEntity<Object> addReceipt(Long customerId, Long receiptId) {
        if (customerRepository.findById(customerId).isEmpty())
            throw new IndexOutOfBoundsException(String.format("Customer with id %d was not found", customerId));

        Customer customer = customerRepository.findById(customerId).get();

        if (receiptRepository.findById(receiptId).isEmpty())
            throw new IndexOutOfBoundsException(String.format("Receipt with id %d was not found", receiptId));

        Receipt receipt = receiptRepository.findById(receiptId).get();

        customer.addReceipt(receipt);
        customerRepository.save(customer);

        return ResponseEntity.ok("Hearing aid was added to customer");
    }

    public ResponseEntity<String> addDocument(Long customerId, String documentName) {
        if (customerRepository.findById(customerId).isEmpty())
            throw new IndexOutOfBoundsException(String.format("Customer with id %d was not found", customerId));

        Customer customer = customerRepository.findById(customerId).get();

        if (documentRepository.findByDocName(documentName).isEmpty())
            throw new IndexOutOfBoundsException(String.format("Document with name %s was not found", documentName));

        Document document = documentRepository.findByDocName(documentName).get();

        customer.addDocument(document);
        customerRepository.save(customer);

        return ResponseEntity.ok("Document was added to customer");
    }

    public ResponseEntity<String> removeDocument(Long customerId, String fileName) {
        if (customerRepository.findById(customerId).isEmpty())
            throw new IndexOutOfBoundsException(String.format("Customer with id %d was not found", customerId));

        Customer customer = customerRepository.findById(customerId).get();

        if (documentRepository.findByDocName(fileName).isEmpty())
            throw new IndexOutOfBoundsException(String.format("Document with name %s was not found", fileName));

        Document documentToRemove = customer.getDocumentList().stream().filter((a) -> a.getDocName().equals(fileName)).findAny().get();

        customer.removeDocument(documentToRemove);
        customerRepository.save(customer);

        return ResponseEntity.ok("Document was removed from customer");
    }

    public static Customer toCustomer(CustomerDto dto) {
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
    public static CustomerDto fromCustomer(Customer customer) {
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

