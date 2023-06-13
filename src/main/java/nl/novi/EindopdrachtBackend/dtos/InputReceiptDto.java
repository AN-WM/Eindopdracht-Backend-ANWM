package nl.novi.EindopdrachtBackend.dtos;

import nl.novi.EindopdrachtBackend.models.Customer;

import java.time.LocalDate;
import java.util.Date;

public class InputReceiptDto {
    public Long id;
    public LocalDate saleDate;
    public Customer customer;

    public InputReceiptDto() {
    }

    public InputReceiptDto(long id) {
        this.id = id;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
