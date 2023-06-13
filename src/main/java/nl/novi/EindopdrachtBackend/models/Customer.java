package nl.novi.EindopdrachtBackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String address;
    private String zipCode;
    private String city;
    private int phoneNumber;
    private String email;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Receipt> receiptList;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<HearingAid> hearingAidList;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Document> documentList;

    public Customer(){};

    public Customer(Long id,
                    String firstName,
                    String lastName,
                    LocalDate dob,
                    String address,
                    String zipCode,
                    String city,
                    int phoneNumber,
                    String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.address = address;
        this.zipCode = zipCode;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Customer(Long id,
                    String firstName,
                    String lastName,
                    LocalDate dob,
                    String address,
                    String zipCode,
                    String city,
                    int phoneNumber,
                    String email,
                    List<Receipt> receiptList,
                    List<HearingAid> hearingAidList,
                    List<Document> documentList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.address = address;
        this.zipCode = zipCode;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.receiptList = receiptList;
        this.hearingAidList = hearingAidList;
        this.documentList = documentList;
    }

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

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
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

    public List<Receipt> getReceiptList() {
        return receiptList;
    }

    public void setReceiptList(List<Receipt> receiptList) {
        this.receiptList = receiptList;
    }

    public void addReceipt(Receipt receipt) {
        this.receiptList.add(receipt);
    }

    public void removeReceipt(Receipt receipt) {
        this.receiptList.remove(receipt);
    }

    public List<Document> getDocumentList(){
        return documentList;
    }

    public void setDocumentList(List<Document> documentList) {
        this.documentList = documentList;
    }

    public void addDocument(Document document){
        this.documentList.add(document);
    }

    public void removeDocument(Document document){
        this.documentList.remove(document);
    }
}
