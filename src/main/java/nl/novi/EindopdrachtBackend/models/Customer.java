package nl.novi.EindopdrachtBackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String zipCode;
    private String city;
    private int phoneNumber;
    private String email;
    //private doc document

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Receipt> receiptList;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<HearingAid> hearingAidList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Receipt> getReceiptList() {
        return receiptList;
    }

    public void setReceiptList(List<Receipt> receiptList) {
        this.receiptList = receiptList;
    }

    public List<HearingAid> getHearingAidList() {
        return hearingAidList;
    }

    public void setHearingAidList(List<HearingAid> hearingAidList) {
        this.hearingAidList = hearingAidList;
    }

    public void addHearingAid(HearingAid hearingAid) {
        this.hearingAidList.add(hearingAid);
    }

    public void removeHearingAid(HearingAid hearingAid) {
        this.hearingAidList.remove(hearingAid);
    }
}
