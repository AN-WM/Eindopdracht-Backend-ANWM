package nl.novi.EindopdrachtBackend.controllers;

import nl.novi.EindopdrachtBackend.dtos.CustomerDto;
import nl.novi.EindopdrachtBackend.dtos.DocumentDto;
import nl.novi.EindopdrachtBackend.services.CustomerService;
import nl.novi.EindopdrachtBackend.services.DocumentService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final DocumentService documentService;

    public CustomerController(CustomerService customerService,
                              DocumentService documentService) {
        this.customerService = customerService;
        this.documentService = documentService;
    }

    //Customer requests
    @PostMapping(value = "")
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto dto) {
        return customerService.createCustomer(dto);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<CustomerDto>> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping(value = "/{customerId}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("customerId") Long customerId) {
        return customerService.getCustomer(customerId);
    }

    @PutMapping(value = "/{customerId}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("customerId") Long customerId, @RequestBody CustomerDto dto) {
        return customerService.updateCustomer(customerId, dto);
    }

    @DeleteMapping(value = "/{customerId}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable("customerId") Long customerId) {
        return customerService.deleteCustomer(customerId);
    }

    // Document requests
    @PostMapping(value = "/{customerId}/documents")
    public ResponseEntity<String> addDocumentToCustomer(@PathVariable("customerId") Long customerId, @RequestBody MultipartFile file) throws IOException {
        String newDocumentName = documentService.uploadDocument(customerId, file);
        return customerService.addDocument(customerId, newDocumentName);
    }

    @GetMapping(value = "/{customerId}/documents")
    public ResponseEntity<List<DocumentDto>> getAllDocuments(@PathVariable("customerId") Long customerId){
        return documentService.getAllDocuments(customerId);
    }

    @DeleteMapping(value = "/{customerId}/{docName}")
    public ResponseEntity<String> deleteDocument(@PathVariable("customerId") Long customerId, @PathVariable("docName") String docName) {
        return customerService.removeDocument(customerId, docName);
    }
}
