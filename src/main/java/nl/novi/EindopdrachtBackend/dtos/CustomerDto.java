package nl.novi.EindopdrachtBackend.dtos;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class CustomerDto {
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private LocalDate dob;
    private String address;
    private String zipCode;
    private String city;
    private int phoneNumber;
    @Email
    private String email;
    //private doc document

    public CustomerDto() {
    }

    public CustomerDto(long id, String firstName, String lastName, LocalDate dob, String address, String zipCode, String city, int phoneNumber, String email) {
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
}
