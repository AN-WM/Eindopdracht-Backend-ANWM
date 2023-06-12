package nl.novi.EindopdrachtBackend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "earpieces")
public class EarPiece {
    @Id
    @GeneratedValue
    private Long id;
    private String type;
    private String colour;
    private String size;
    private Double price;

    @ManyToOne
    @JoinColumn(name="receipt_id")
    private Receipt receipt;

    @OneToOne
    private HearingAid hearingAid;

    public EarPiece(){}

    public EarPiece(long id, String type, String colour, String size, double price) {
        this.id = id;
        this.type = type;
        this.colour = colour;
        this.size = size;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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

    public HearingAid getHearingAid() {
        return hearingAid;
    }

    public void setHearingAid(HearingAid hearingAid) {
        this.hearingAid = hearingAid;
    }
}
