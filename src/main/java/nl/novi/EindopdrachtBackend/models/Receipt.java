package nl.novi.EindopdrachtBackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "receipts")
public class Receipt {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDate saleDate;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @OneToMany
    @JsonIgnore
    private List<HearingAid> hearingAidList;

    @OneToMany
    @JsonIgnore
    private List<EarPiece> earPieceList;

    public Receipt() {
    }

    public Receipt(Long id) {
        this.id = id;
    }

    public Receipt(long id, Customer customer, List<HearingAid> hearingAidList, List<EarPiece> earPieceList) {
        this.id = id;
        this.customer = customer;
        this.hearingAidList = hearingAidList;
        this.earPieceList = earPieceList;
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

    public void addEarPiece(EarPiece earPiece) {
        this.earPieceList.add(earPiece);
    }

    public void removeEarPiece(EarPiece earPiece) {
        this.earPieceList.remove(earPiece);
    }
    
    public void addHearingAid(HearingAid hearingAid) {
        this.hearingAidList.add(hearingAid);
    }

    public void removeHearingAid(HearingAid hearingAid) {
        this.hearingAidList.remove(hearingAid);
    }

}
