package nl.novi.EindopdrachtBackend.models;

import jakarta.persistence.*;
@Entity
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue
    private Long id;
    private String docName;
    private byte[] docFile;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    public Document(){}
    public Document(long id, String docName) {
        this.id = id;
        this.docName = docName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocName() {
        return docName;
    }

    public byte[] getDocFile() {
        return docFile;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public void setDocFile(byte[] docFile) {
        this.docFile = docFile;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
