package nl.novi.EindopdrachtBackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "receipts")
public class Receipt {
    @Id
    @GeneratedValue
    private Long id;
    private Date saleDate;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @OneToMany
    @JsonIgnore
    private List<HearingAid> hearingAidList;

    @OneToMany
    @JsonIgnore
    private List<EarPiece> earPieceList;

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

    public List<HearingAid> getHearingAidList() {
        return hearingAidList;
    }

    public void setHearingAidList(List<HearingAid> hearingAidList) {
        this.hearingAidList = hearingAidList;
    }

    public List<EarPiece> getEarPieceList() {
        return earPieceList;
    }

    public void setEarPieceList(List<EarPiece> earPieceList) {
        this.earPieceList = earPieceList;
    }
}
