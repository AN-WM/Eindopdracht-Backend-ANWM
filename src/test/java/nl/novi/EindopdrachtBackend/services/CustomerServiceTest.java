package nl.novi.EindopdrachtBackend.services;

import nl.novi.EindopdrachtBackend.dtos.CustomerDto;
import nl.novi.EindopdrachtBackend.models.Customer;
import nl.novi.EindopdrachtBackend.models.EarPiece;
import nl.novi.EindopdrachtBackend.models.HearingAid;
import nl.novi.EindopdrachtBackend.repositories.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;

class CustomerServiceTest {
    //Maak een mockobject aan
    @Mock
    CustomerRepository repos;

    //Gebruik de mockobjecten in dit serviceobject
    @InjectMocks
    CustomerService service;

    Customer c;
    HearingAid h;
    EarPiece e;
    List<HearingAid> hl;
    List<EarPiece> ep;

    @BeforeEach
    void setup() {
        c = new Customer();
        h = new HearingAid();
        e = new EarPiece();


        c.setId(100L);
        c.setFirstName("Beta");
        c.setLastName("Tester");
        c.setAddress("Molenplein 1");
        c.setZipCode("1234 BT");
        c.setCity("De Stad");
        c.setPhoneNumber(1234567890);
        c.setEmail("betatester@home.nl");
    }

    @AfterEach
    void tearDown() {
        c = null;
        h = null;
        e = null;
    }

    @Test
    void getAllCustomers() {

        Mockito.when(repos.findById(anyLong())).thenReturn(Optional.of(c));

        //Act
        List<CustomerDto> customerList = service.getAllCustomers();

        //Assert
        assertEquals(c, customerList);
    }

    @Test
    void getCustomer() {
    }

    @Test
    void createCustomer() {
    }

    @Test
    void updateCustomer() {
    }

    @Test
    void deleteCustomer() {
    }

    @Test
    void addHearingAid() {
    }

    @Test
    void removeHearingAid() {
    }

    @Test
    void addReceipt() {
    }

    @Test
    void toCustomer() {
    }

    @Test
    void fromCustomer() {
    }
}