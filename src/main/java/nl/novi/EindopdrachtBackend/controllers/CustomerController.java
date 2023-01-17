package nl.novi.EindopdrachtBackend.controllers;

import nl.novi.EindopdrachtBackend.dtos.CustomerDto;
import nl.novi.EindopdrachtBackend.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<CustomerDto>> getCustomers() {

        List<CustomerDto> customerDtos = customerService.getAllCustomers();

        return ResponseEntity.ok().body(customerDtos);
    }

    @GetMapping(value = "/{customerId}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("customerId") Long customerId) {

        CustomerDto optionalCustomer = customerService.getCustomer(customerId);

        return ResponseEntity.ok().body(optionalCustomer);
    }

    @PostMapping(value = "")
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto dto) {;

        Long newCustomerId = customerService.createCustomer(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{customerId}")
                .buildAndExpand(newCustomerId).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/{customerId}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("customerId") Long customerId, @RequestBody CustomerDto dto) {

        customerService.updateCustomer(customerId, dto);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{customerId}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable("customerId") Long customerId) {

        customerService.deleteCustomer(customerId);

        return ResponseEntity.noContent().build();
    }
}
