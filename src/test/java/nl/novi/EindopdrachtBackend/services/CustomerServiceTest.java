package nl.novi.EindopdrachtBackend.services;

import nl.novi.EindopdrachtBackend.dtos.CustomerDto;
import nl.novi.EindopdrachtBackend.models.Customer;
import nl.novi.EindopdrachtBackend.models.EarPiece;
import nl.novi.EindopdrachtBackend.models.HearingAid;
import nl.novi.EindopdrachtBackend.models.Receipt;
import nl.novi.EindopdrachtBackend.repositories.CustomerRepository;
import nl.novi.EindopdrachtBackend.repositories.HearingAidRepository;
import nl.novi.EindopdrachtBackend.repositories.ReceiptRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;
    @Mock
    HearingAidRepository hearingAidRepository;
    @Mock
    ReceiptRepository receiptRepository;

    @InjectMocks
    CustomerService customerService;

    @Captor
    ArgumentCaptor<Customer> captor;

    Customer customer1;
    Customer customer2;
    Customer updatedCustomer;
    CustomerDto customerDto1;
    CustomerDto updateDto;
    HearingAid hearingAid1;
    HearingAid hearingAid2;
    List<HearingAid> hearingAidList;
    Receipt receipt1;
    List<Receipt> receiptList;

    @BeforeEach
    void setup() {
        hearingAid1 = new HearingAid("tha1", "Oticon", "Real 1 miniRITE R", "Auburn", 2075);
        hearingAid2 = new HearingAid("tha2", "Widex", "Moment Sheer 440 sRIC R D", "Beige", 1995);
        receipt1 = new Receipt(1L, new Date(01 - 01 - 2023));
        hearingAidList = new ArrayList<>();
        receiptList = new ArrayList<>();
        hearingAidList.add(hearingAid1);
        customer1 = new Customer(1L, "Alfa", "Tests", "Dorpsstraat 1", "9876 ZY", "Het Gehucht", 1928376450, "alfatests@gmail.com");
        customer2 = new Customer(2L, "Beta", "Tester", "Molenplein 1", "1234 AB", "De Stad", 1234567890, "betatester@home.nl", receiptList, hearingAidList);
        updatedCustomer = new Customer(1L, "Alfa", "de Tester", "Dorpsstraat 1", "9876 ZY", "Het Gehucht", 1928376450, "alfatests@gmail.com");
        customerDto1 = new CustomerDto(1L, "Alfa", "Tests", "Dorpsstraat 1", "9876 ZY", "Het Gehucht", 1928376450, "alfatests@gmail.com");
        updateDto = new CustomerDto(1L, "Alfa", "de Tester", "Dorpsstraat 1", "9876 ZY", "Het Gehucht", 1928376450, "alfatests@gmail.com");
    }

    @AfterEach
    void tearDown() {
        hearingAid1 = null;
        hearingAid2 = null;
        receipt1 = null;
        hearingAidList = null;
        receiptList = null;
        customer1 = null;
        customer2 = null;
        updatedCustomer = null;
        customerDto1 = null;
        updateDto = null;
    }

    @Test
    public void testGetSingleCustomer() {
        //Arrange
        Mockito
                .when(customerRepository.findById(customer1.getId()))
                .thenReturn(Optional.ofNullable(customer1));

        //Act
        CustomerDto result = customerService.getCustomer(1L);

        //Assert
        assertEquals("Alfa", result.getFirstName());
        assertEquals("Tests", result.getLastName());
    }

    @Test
    void testGetThrowsException() {
        assertThrows(
                nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException.class,
                () -> customerService.getCustomer(4L)
        );
    }

    @Test
    void getAllCustomers() {
        Mockito.when(customerRepository.findAll()).thenReturn(List.of(customer1, customer2));

        List<CustomerDto> customerList = customerService.getAllCustomers();

        assertEquals(customer1.getId(), customerList.get(0).getId());
        assertEquals(customer2.getId(), customerList.get(1).getId());
    }

    @Test
    void testCreateCustomer() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer1);

        customerService.createCustomer(customerDto1);
        verify(customerRepository, times(1)).save(captor.capture());
        Customer result = captor.getValue();

        assertEquals(customer1.getId(), result.getId());
        assertEquals(customer1.getFirstName(), result.getFirstName());
        assertEquals(customer1.getLastName(), result.getLastName());
        assertEquals(customer1.getAddress(), result.getAddress());
        assertEquals(customer1.getZipCode(), result.getZipCode());
        assertEquals(customer1.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(customer1.getEmail(), result.getEmail());
    }

    @Test
    void testUpdateCustomer() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer1);

        customerService.updateCustomer(1L, updateDto);
        verify(customerRepository, times(1)).save(captor.capture());

        Customer result = captor.getValue();

        assertEquals(updatedCustomer.getId(), result.getId());
        assertEquals(updatedCustomer.getFirstName(), result.getFirstName());
        assertEquals(updatedCustomer.getLastName(), result.getLastName());
        assertEquals(updatedCustomer.getAddress(), result.getAddress());
        assertEquals(updatedCustomer.getZipCode(), result.getZipCode());
        assertEquals(updatedCustomer.getCity(), result.getCity());
        assertEquals(updatedCustomer.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(updatedCustomer.getEmail(), result.getEmail());
    }

    @Test
    void testUpdateThrowsException() {
        assertThrows(nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException.class,
                () -> customerService.updateCustomer(4L, updateDto));
    }

    @Test
    void testDeleteCustomer() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));

        customerService.deleteCustomer(1L);

        verify(customerRepository).deleteById(1L);
    }

    @Test
    void testDeleteThrowsException() {
        assertThrows(nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException.class,
                () -> customerService.deleteCustomer(4L));
    }


    @Test
    void testAddHearingAid() {
        when(customerRepository.findById(2L)).thenReturn(Optional.of(customer2));
        when(hearingAidRepository.findById("tha2")).thenReturn(Optional.of(hearingAid2));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer2);

        customerService.addHearingAid(2L, "tha2");
        verify(customerRepository, times(1)).save(captor.capture());
        Customer result = captor.getValue();

        assertEquals(2L, result.getId());
        assertEquals("tha2", result.getHearingAidList().get(1).getProductcode());
    }

    @Test
    void testAddHearingAidThrowsCustomerException() {
        assertThrows(nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException.class,
                () -> customerService.addHearingAid(4L, "tha2"));
    }
    @Test
    void testAddHearingAidThrowsHearingAidException() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));

        assertThrows(nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException.class,
                () -> customerService.addHearingAid(1L, "tha3"));
    }

    @Test
    void testRemoveHearingAid() {
        when(customerRepository.findById(2L)).thenReturn(Optional.of(customer2));
        when(hearingAidRepository.findById("tha1")).thenReturn(Optional.of(hearingAid1));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer2);

        customerService.removeHearingAid(2L, "tha1");
        verify(customerRepository, times(1)).save(captor.capture());
        Customer result = captor.getValue();

        assertEquals(2L, result.getId());
        assertEquals(true, result.getHearingAidList().isEmpty());
    }

    @Test
    void testRemoveHearingAidThrowsCustomerException() {
        assertThrows(nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException.class,
                () -> customerService.removeHearingAid(4L, "tha2"));
    }
    @Test
    void testRemoveHearingAidThrowsHearingAidException() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));

        assertThrows(nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException.class,
                () -> customerService.removeHearingAid(1L, "tha3"));
    }
    @Test
    void testAddReceipt() {
        when(customerRepository.findById(2L)).thenReturn(Optional.of(customer2));
        when(receiptRepository.findById(1L)).thenReturn(Optional.of(receipt1));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer2);

        customerService.addReceipt(2L, 1L);
        verify(customerRepository, times(1)).save(captor.capture());
        Customer result = captor.getValue();

        assertEquals(2L, result.getId());
        assertEquals(1L, result.getReceiptList().get(0).getId());
    }

    @Test
    void testAddReceiptThrowsCustomerException() {
        assertThrows(nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException.class,
                () -> customerService.addReceipt(4L, 1L));
    }
    @Test
    void testAddReceiptThrowsReceiptException() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));

        assertThrows(nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException.class,
                () -> customerService.addReceipt(1L, 3L));
    }
}