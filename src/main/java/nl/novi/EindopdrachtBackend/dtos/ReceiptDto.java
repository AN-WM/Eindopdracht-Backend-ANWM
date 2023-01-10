package nl.novi.EindopdrachtBackend.dtos;

import java.util.Date;

public class ReceiptDto {
    private Date saleDate;

    private ReceiptDto() {
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }
}
