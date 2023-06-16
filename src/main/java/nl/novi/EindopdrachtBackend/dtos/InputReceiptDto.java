package nl.novi.EindopdrachtBackend.dtos;

import nl.novi.EindopdrachtBackend.models.Customer;

import java.time.LocalDate;

public class InputReceiptDto {
    public Long id;
    public LocalDate saleDate;
    public CustomerDto customerDto;

    public InputReceiptDto() {
    }

    public InputReceiptDto(long id) {
        this.id = id;
    }

    public InputReceiptDto(long id, CustomerDto customerDto) {
        this.id = id;
        this.customerDto = customerDto;
    }

    public InputReceiptDto(long id, LocalDate saleDate, CustomerDto customerDto) {
        this.id = id;
        this.saleDate = saleDate;
        this.customerDto = customerDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public CustomerDto getCustomerDto() {
        return customerDto;
    }

    public void setCustomerDto(CustomerDto customerDto) {
        this.customerDto = customerDto;
    }
}
