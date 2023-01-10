package nl.novi.EindopdrachtBackend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "hearingaids")
public class HearingAid {
    @Id
    private String productcode;
    private String brand;
    private String type;
    private String colour;
    private Double price;

    @ManyToOne
    @JoinColumn(name="receipt_id")
    private Receipt receipt;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @OneToOne(mappedBy = "hearingAid")
    private EarPiece earPiece;

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public EarPiece getEarPiece() {
        return earPiece;
    }

    public void setEarPiece(EarPiece earPiece) {
        this.earPiece = earPiece;
    }
}
