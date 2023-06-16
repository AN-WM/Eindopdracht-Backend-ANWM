package nl.novi.EindopdrachtBackend.integrationtests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.novi.EindopdrachtBackend.dtos.CustomerDto;
import nl.novi.EindopdrachtBackend.dtos.EarPieceDto;
import nl.novi.EindopdrachtBackend.dtos.InputReceiptDto;
import nl.novi.EindopdrachtBackend.models.Customer;
import nl.novi.EindopdrachtBackend.models.EarPiece;
import nl.novi.EindopdrachtBackend.models.HearingAid;
import nl.novi.EindopdrachtBackend.models.Receipt;
import nl.novi.EindopdrachtBackend.repositories.CustomerRepository;
import nl.novi.EindopdrachtBackend.repositories.EarPieceRepository;
import nl.novi.EindopdrachtBackend.repositories.HearingAidRepository;
import nl.novi.EindopdrachtBackend.repositories.ReceiptRepository;
import nl.novi.EindopdrachtBackend.services.ReceiptService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class ReceiptIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private ReceiptRepository receiptRepository;
    @Autowired
    private HearingAidRepository hearingAidRepository;
    @Autowired
    private EarPieceRepository earPieceRepository;
    @Autowired
    private CustomerRepository customerRepository;

    Receipt receipt1;
    Receipt receipt2;
    Receipt receipt3;
    Customer customer1;
    CustomerDto customerDto1;
    HearingAid hearingAid1;
    HearingAid hearingAid2;
    List<HearingAid> hearingAidList;
    EarPiece earPiece1;
    EarPiece earPiece2;
    List<EarPiece> earPieceList;
    InputReceiptDto updateDto;
    Receipt updatedReceipt;

    @BeforeEach
    public void setUp() {
        customer1 = new Customer(1L, "Alfa", "Tests", LocalDate.of(1990, 05, 06), "Dorpsstraat 1", "9876 ZY", "Het Gehucht", 1928376450, "alfatests@gmail.com");
        customerDto1 = new CustomerDto(1L, "Alfa", "Tests", LocalDate.of(1990, 05, 06), "Dorpsstraat 1", "9876 ZY", "Het Gehucht", 1928376450, "alfatests@gmail.com");
        customerRepository.save(customer1);

        hearingAid1 = new HearingAid("tha1", "Oticon", "Real 1 miniRITE R", "Auburn", 2075);
        hearingAid2 = new HearingAid("tha2", "Widex", "Moment Sheer 440 sRIC R D", "Beige", 1995);
        hearingAidRepository.save(hearingAid1);
        hearingAidRepository.save(hearingAid2);

        earPiece1 = new EarPiece(1L, "Custom", "Brown", "Custom", 80.94);
        earPiece2 = new EarPiece(1L, "Dome", "White", "40", 0.50);
        earPieceRepository.save(earPiece1);
        earPieceRepository.save(earPiece2);

        hearingAidList = new ArrayList<>();
        earPieceList = new ArrayList<>();
        hearingAidList.add(hearingAid1);
        earPieceList.add(earPiece1);

        receipt1 = new Receipt(1L);
        receipt2 = new Receipt(2L, customer1, hearingAidList, earPieceList);
        receipt3 = new Receipt(3L);
        updateDto = new InputReceiptDto(1L);
        updatedReceipt = new Receipt(1L);

        receiptRepository.save(receipt1);
        receiptRepository.save(receipt2);
        receiptRepository.save(receipt3);
    }

    @AfterEach
    public void tearDown(){
        customer1 = null;
        customerDto1 = null;

        hearingAid1 = null;
        hearingAid2 = null;

        earPiece1 = null;
        earPiece2 = null;

        hearingAidList = null;
        earPieceList = null;

        receipt1 = null;
        receipt2 = null;
        updateDto = null;
        updatedReceipt = null;
    }

    @Test
    void testGetAllReceipts() throws Exception {
        mockMvc.perform(get("/receipts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(receipt1.getId().toString()))
                .andExpect(jsonPath("$[1].id").value(receipt2.getId().toString()))
                .andExpect(jsonPath("$[1].customerDto.id").value( customerDto1.getId()))
                .andExpect(jsonPath("$[1].hearingAidDtoList[0].productcode").value(hearingAidList.get(0).getProductcode()))
                .andExpect(jsonPath("$[1].earPieceDtoList[0].id").value(earPieceList.get(0).getId().toString()));
    }

    @Test
    void testGetSingleReceipt() throws Exception {

        mockMvc.perform(get("/receipts/" + receipt1.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(receipt1.getId().toString()));
    }

    @Test
    void testSaveReceipt() throws Exception {
        mockMvc.perform(post("/receipts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updateDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("New receipt created with id 1"));
    }

    @Test
    void updateReceipt() throws Exception {
        mockMvc.perform(put("/receipts/" + receipt1.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(updatedReceipt.getId().toString()));
    }

    @Test
    void deleteReceipt() throws Exception {
        mockMvc.perform(delete("/receipts/" + receipt3.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().string(String.format("Receipt with id %d was removed from the database", receipt3.getId())));
    }

    public static String asJsonString(final InputReceiptDto obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
