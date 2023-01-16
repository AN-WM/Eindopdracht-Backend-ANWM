package nl.novi.EindopdrachtBackend.dtos;

import java.util.Date;

public class ReceiptDto {
    public Long id;
    public Date saleDate;

    public ReceiptDto() {
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
}
