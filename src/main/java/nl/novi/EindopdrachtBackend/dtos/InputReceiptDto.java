package nl.novi.EindopdrachtBackend.dtos;

import nl.novi.EindopdrachtBackend.models.Customer;

import java.util.Date;

public class InputReceiptDto {
    public Long id;
    public Date saleDate;
    public Customer customer;

    public InputReceiptDto() {
    }

    public InputReceiptDto(long id  , Date saleDate) {
        this.id = id;
        this.saleDate = saleDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
