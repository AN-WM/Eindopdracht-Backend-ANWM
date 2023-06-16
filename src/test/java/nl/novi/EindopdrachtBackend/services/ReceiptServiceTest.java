package nl.novi.EindopdrachtBackend.services;

import nl.novi.EindopdrachtBackend.dtos.CustomerDto;
import nl.novi.EindopdrachtBackend.dtos.InputReceiptDto;
import nl.novi.EindopdrachtBackend.dtos.ReturnReceiptDto;
import nl.novi.EindopdrachtBackend.models.Customer;
import nl.novi.EindopdrachtBackend.models.EarPiece;
import nl.novi.EindopdrachtBackend.models.HearingAid;
import nl.novi.EindopdrachtBackend.models.Receipt;
import nl.novi.EindopdrachtBackend.repositories.CustomerRepository;
import nl.novi.EindopdrachtBackend.repositories.EarPieceRepository;
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
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ReceiptServiceTest {

    @Mock
    CustomerRepository customerRepository;
    @Mock
    HearingAidRepository hearingAidRepository;
    @Mock
    EarPieceRepository earPieceRepository;
    @Mock
    ReceiptRepository receiptRepository;

    @InjectMocks
    ReceiptService receiptService;

    @Captor
    ArgumentCaptor<Receipt> captor;

    Customer customer1;
    CustomerDto customerDto;
    HearingAid hearingAid1;
    HearingAid hearingAid2;
    List<HearingAid> hearingAidList;
    EarPiece earPiece1;
    List<EarPiece> earPieceList;
    Receipt receipt1;
    Receipt receipt2;
    InputReceiptDto inputReceiptDto1;
    InputReceiptDto updateDto1;
    InputReceiptDto updateDto2;
    Receipt updatedReceipt;
    LocalDate testDate;

    @BeforeEach
    void setUp() {
        hearingAid1 = new HearingAid("tha1", "Oticon", "Real 1 miniRITE R", "Auburn", 2075);
        hearingAid2 = new HearingAid("tha2", "Widex", "Moment Sheer 440 sRIC R D", "Beige", 1995);
        earPiece1 = new EarPiece(1L, "Custom", "Brown", "Custom", 80.94);
        receipt1 = new Receipt(1L);
        inputReceiptDto1 = new InputReceiptDto(1L);
        hearingAidList = new ArrayList<>();
        earPieceList = new ArrayList<>();
        hearingAidList.add(hearingAid1);
        earPieceList.add(earPiece1);
        customer1 = new Customer(1L, "Alfa", "Tests", LocalDate.of(1990, 05, 06), "Dorpsstraat 1", "9876 ZY", "Het Gehucht", 1928376450, "alfatests@gmail.com");
        customerDto = new CustomerDto(1L, "Alfa", "Tests", LocalDate.of(1990, 05, 06), "Dorpsstraat 1", "9876 ZY", "Het Gehucht", 1928376450, "alfatests@gmail.com");
        updateDto1 =  new InputReceiptDto(1L, LocalDate.of(1990, 04,02), customerDto);
        updateDto2 =  new InputReceiptDto(5L, LocalDate.of(1990, 04,02), customerDto);
        updatedReceipt = new Receipt(1L);
        receipt2 = new Receipt(2L, customer1, hearingAidList, earPieceList);
        testDate = LocalDate.now();
    }

    @AfterEach
    void tearDown() {
        hearingAid1 = null;
        hearingAid2 = null;
        receipt1 = null;
        receipt2 = null;
        inputReceiptDto1 = null;
        hearingAidList = null;
        customer1 = null;
        updateDto1 = null;
        updateDto2 = null;
        updatedReceipt = null;
        testDate = null;
    }

    @Test
    void testGetAllReceipts() {
        //Arrange
        Mockito.when(receiptRepository.findAll()).thenReturn(List.of(receipt1, receipt2));

        //Act
        List<ReturnReceiptDto> receiptList = receiptService.getAllReceipts().getBody();

        //Assert
        assertEquals(receipt1.getId(), receiptList.get(0).getId());
        assertEquals(receipt2.getId(), receiptList.get(1).getId());
    }

    @Test
    void testGetSingleReceipt() {
        Mockito
                .when(receiptRepository.findById(receipt1.getId()))
                .thenReturn(Optional.ofNullable(receipt1));

        ReturnReceiptDto result = receiptService.getReceipt(1L).getBody();

        assertEquals(1L, result.getId());
    }

    @Test
    void testGetThrowsException() {
        assertThrows(nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException.class,
                () -> receiptService.getReceipt(4L));
    }

    @Test
    void testCreateReceipt() {
        when(receiptRepository.save(any(Receipt.class))).thenReturn(receipt1);

        receiptService.createReceipt(inputReceiptDto1);
        verify(receiptRepository, times(1)).save(captor.capture());
        Receipt result = captor.getValue();

        assertEquals(receipt1.getId(), result.getId());
        assertEquals(receipt1.getSaleDate(), result.getSaleDate());
    }

    @Test
    void testUpdateReceipt() {
        when(receiptRepository.findById(1L)).thenReturn(Optional.of(receipt1));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));

        receiptService.updateReceipt(1L, updateDto1);
        verify(receiptRepository, times(1)).save(captor.capture());

        Receipt result = captor.getValue();

        assertEquals(updatedReceipt.getId(), result.getId());
        assertEquals(LocalDate.of(1990, 04,02), result.getSaleDate());
    }

    @Test
    void testUpdateThrowsReceiptException() {
        assertThrows(nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException.class,
                () -> receiptService.updateReceipt(4L, updateDto1));
    }

    @Test
    void testUpdateThrowsCustomerException() {
        when(receiptRepository.findById(1L)).thenReturn(Optional.of(receipt1));

        assertThrows(nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException.class,
                () -> receiptService.updateReceipt(1L, updateDto2));
    }

    @Test
    void testFinaliseReceipt() {
        when(receiptRepository.findById(1L)).thenReturn(Optional.of(receipt1));

        receiptService.finaliseReceipt(1L, testDate);
        verify(receiptRepository, times(1)).save(captor.capture());

        Receipt result = captor.getValue();

        assertEquals(updatedReceipt.getId(), result.getId());
        assertEquals(testDate, result.getSaleDate());
    }

    @Test
    void testFinaliseReceiptThrowsException() {
        assertThrows(nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException.class,
                () -> receiptService.finaliseReceipt(4L, testDate));
    }

    @Test
    void testDeleteReceipt() {
        when(receiptRepository.findById(2L)).thenReturn(Optional.of(receipt2));

        receiptService.deleteReceipt(2L);

        verify(receiptRepository).deleteById(2L);
    }

    @Test
    void testDeleteThrowsException() {
        assertThrows(nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException.class,
                () -> receiptService.deleteReceipt(4L));
    }


    @Test
    void testAddCustomer() {
        when(receiptRepository.findById(2L)).thenReturn(Optional.of(receipt2));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));
        when(receiptRepository.save(any(Receipt.class))).thenReturn(receipt2);

        receiptService.addCustomer(2L, 1L);
        verify(receiptRepository, times(1)).save(captor.capture());
        Receipt result = captor.getValue();

        assertEquals(2L, result.getId());
        assertEquals(1L, result.getCustomer().getId());
    }

    @Test
    void testAddCustomerThrowsReceiptException() {
        assertThrows(nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException.class,
                () -> receiptService.addCustomer(4L, 1L));
    }
    @Test
    void testAddCustomerThrowsCustomerException() {
        when(receiptRepository.findById(1L)).thenReturn(Optional.of(receipt1));

        assertThrows(nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException.class,
                () -> receiptService.addCustomer(1L, 4L));
    }

    @Test
    void testAddEarPiece() {
        when(receiptRepository.findById(2L)).thenReturn(Optional.of(receipt2));
        when(earPieceRepository.findById(1L)).thenReturn(Optional.of(earPiece1));
        when(receiptRepository.save(any(Receipt.class))).thenReturn(receipt2);

        receiptService.addEarPiece(2L, 1L);
        verify(receiptRepository, times(1)).save(captor.capture());
        Receipt result = captor.getValue();

        assertEquals(2L, result.getId());
        assertEquals(1L, result.getEarPieceList().get(0).getId());
    }

    @Test
    void testAddEarPieceThrowsReceiptException() {
        assertThrows(nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException.class,
                () -> receiptService.addEarPiece(4L, 1L));
    }
    @Test
    void testAddEarPieceThrowsEarPieceException() {
        when(receiptRepository.findById(1L)).thenReturn(Optional.of(receipt1));

        assertThrows(nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException.class,
                () -> receiptService.addEarPiece(1L, 4L));
    }

    @Test
    void testRemoveEarPiece() {
        when(receiptRepository.findById(2L)).thenReturn(Optional.of(receipt2));
        when(earPieceRepository.findById(1L)).thenReturn(Optional.of(earPiece1));
        when(receiptRepository.save(any(Receipt.class))).thenReturn(receipt2);

        receiptService.removeEarPiece(2L, 1L);
        verify(receiptRepository, times(1)).save(captor.capture());
        Receipt result = captor.getValue();

        assertEquals(2L, result.getId());
        assertTrue(result.getEarPieceList().isEmpty());
    }

    @Test
    void testRemoveEarPieceThrowsReceiptException() {
        assertThrows(nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException.class,
                () -> receiptService.removeEarPiece(4L, 1L));
    }
    @Test
    void testRemoveEarPieceThrowsEarPieceException() {
        when(receiptRepository.findById(1L)).thenReturn(Optional.of(receipt1));

        assertThrows(nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException.class,
                () -> receiptService.removeEarPiece(1L, 4L));
    }

    @Test
    void addHearingAid() {
        when(receiptRepository.findById(2L)).thenReturn(Optional.of(receipt2));
        when(hearingAidRepository.findById("tha2")).thenReturn(Optional.of(hearingAid2));
        when(receiptRepository.save(any(Receipt.class))).thenReturn(receipt2);

        receiptService.addHearingAid(2L, "tha2");
        verify(receiptRepository, times(1)).save(captor.capture());
        Receipt result = captor.getValue();

        assertEquals(2L, result.getId());
        assertEquals("tha2", result.getHearingAidList().get(1).getProductcode());
    }

    @Test
    void testAddHearingAidThrowsReceiptException() {
        assertThrows(nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException.class,
                () -> receiptService.addHearingAid(4L, "tha2"));
    }
    @Test
    void testAddHearingAidThrowsHearingAidException() {
        when(receiptRepository.findById(1L)).thenReturn(Optional.of(receipt1));

        assertThrows(nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException.class,
                () -> receiptService.addHearingAid(1L, "tha3"));
    }

    @Test
    void testRemoveHearingAid() {
        when(receiptRepository.findById(2L)).thenReturn(Optional.of(receipt2));
        when(hearingAidRepository.findById("tha1")).thenReturn(Optional.of(hearingAid1));
        when(receiptRepository.save(any(Receipt.class))).thenReturn(receipt2);

        receiptService.removeHearingAid(2L, "tha1");
        verify(receiptRepository, times(1)).save(captor.capture());
        Receipt result = captor.getValue();

        assertEquals(2L, result.getId());
        assertTrue(result.getHearingAidList().isEmpty());
    }

    @Test
    void testRemoveHearingAidThrowsReceiptException() {
        assertThrows(nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException.class,
                () -> receiptService.removeHearingAid(4L, "tha2"));
    }
    @Test
    void testRemoveHearingAidThrowsHearingAidException() {
        when(receiptRepository.findById(1L)).thenReturn(Optional.of(receipt1));

        assertThrows(nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException.class,
                () -> receiptService.removeHearingAid(1L, "tha3"));
    }
}