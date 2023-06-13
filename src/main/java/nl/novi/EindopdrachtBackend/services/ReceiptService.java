package nl.novi.EindopdrachtBackend.services;

import nl.novi.EindopdrachtBackend.dtos.InputReceiptDto;
import nl.novi.EindopdrachtBackend.dtos.ReturnReceiptDto;
import nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException;
import nl.novi.EindopdrachtBackend.models.*;
import nl.novi.EindopdrachtBackend.repositories.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ReceiptService {

    private final CustomerRepository customerRepository;
    private final EarPieceRepository earPieceRepository;
    private final HearingAidRepository hearingAidRepository;
    private final ReceiptRepository receiptRepository;


    public ReceiptService(
            CustomerRepository customerRepository,
            EarPieceRepository earPieceRepository,
            HearingAidRepository hearingAidRepository,
            ReceiptRepository receiptRepository
    ) {
        this.customerRepository = customerRepository;
        this.earPieceRepository = earPieceRepository;
        this.hearingAidRepository = hearingAidRepository;
        this.receiptRepository = receiptRepository;
    }


    public List<ReturnReceiptDto> getAllReceipts() {
        List<ReturnReceiptDto> collection = new ArrayList<>();
        List<Receipt> list = receiptRepository.findAll();
        for (Receipt receipt : list) {
            collection.add(fromReceipt(receipt));
        }
        return collection;
    }

    public ReturnReceiptDto getReceipt(Long id) {
        Optional<Receipt> receipt = receiptRepository.findById(id);
        if (receipt.isEmpty())
            throw new IndexOutOfBoundsException(String.format("ID %d was not found", id));

        return fromReceipt(receipt.get());
    }

    public Long createReceipt(InputReceiptDto receiptDto) {
        Receipt savedReceipt = receiptRepository.save(toReceipt(receiptDto));
        return savedReceipt.getId();
    }

    public Optional<Receipt> updateReceipt(Long receiptId, InputReceiptDto receiptDto) {
        Optional<Receipt> receipt = receiptRepository.findById(receiptId);
        if (receipt.isEmpty())
            throw new IndexOutOfBoundsException(String.format("Receipt with id %d was not found", receiptId));

        receiptRepository.save(toReceipt(receiptDto));
        return receiptRepository.findById(receiptId);
    }

    public ResponseEntity<Receipt> finaliseReceipt(Long receiptId, LocalDate date) {
        if (receiptRepository.findById(receiptId).isEmpty())
            throw new IndexOutOfBoundsException(String.format("Receipt with id %d was not found", receiptId));

        Receipt finalisedReceipt = receiptRepository.findById(receiptId).get();
        finalisedReceipt.setSaleDate(date);

        receiptRepository.save(finalisedReceipt);

        return ResponseEntity.ok(finalisedReceipt);
    }

    public ResponseEntity<Object> deleteReceipt(Long id) {
        Optional<Receipt> receipt = receiptRepository.findById(id);
        if (receipt.isEmpty())
            throw new IndexOutOfBoundsException(String.format("Receipt with id %d was not found", id));

        receiptRepository.deleteById(id);
        return ResponseEntity.ok(String.format("Receipt with id %d was removed from the database", id));
    }

    public void addCustomer(Long receiptId, Long customerId) {
        if (receiptRepository.findById(receiptId).isEmpty())
            throw new IndexOutOfBoundsException(String.format("Receipt with id %d was not found", receiptId));

        Receipt receipt = receiptRepository.findById(receiptId).get();

        if (customerRepository.findById(customerId).isEmpty())
            throw new IndexOutOfBoundsException(String.format("Customer with id %d was not found", customerId));

        Customer customer = customerRepository.findById(customerId).get();

        receipt.setCustomer(customer);
        receiptRepository.save(receipt);
    }

    public ResponseEntity<Object> addEarPiece(Long receiptId, Long earPieceId) {
        if (receiptRepository.findById(receiptId).isEmpty())
            throw new IndexOutOfBoundsException(String.format("Receipt with id %d was not found", receiptId));

        Receipt receipt = receiptRepository.findById(receiptId).get();

        if (earPieceRepository.findById(earPieceId).isEmpty())
            throw new IndexOutOfBoundsException(String.format("Earpiece with id %d was not found", earPieceId));

        EarPiece earPiece = earPieceRepository.findById(earPieceId).get();

        receipt.addEarPiece(earPiece);
        receiptRepository.save(receipt);

        return ResponseEntity.ok("Earpiece added to receipt");
    }

    public ResponseEntity<Object> removeEarPiece(Long receiptId, Long earPieceId) {
        if (receiptRepository.findById(receiptId).isEmpty())
            throw new IndexOutOfBoundsException(String.format("Receipt with id %d was not found", receiptId));

        Receipt receipt = receiptRepository.findById(receiptId).get();

        if (earPieceRepository.findById(earPieceId).isEmpty())
            throw new IndexOutOfBoundsException(String.format("Earpiece with id %d was not found", earPieceId));

        EarPiece earPieceToRemove = receipt.getEarPieceList().stream().filter((a) -> a.getId().equals(earPieceId)).findAny().get();

        receipt.removeEarPiece(earPieceToRemove);
        receiptRepository.save(receipt);

        return ResponseEntity.ok("Earpiece removed from receipt");
    }

    public ResponseEntity<Object> addHearingAid(Long receiptId, String productcode) {
        if (receiptRepository.findById(receiptId).isEmpty())
            throw new IndexOutOfBoundsException(String.format("Receipt with id %d was not found", receiptId));

        Receipt receipt = receiptRepository.findById(receiptId).get();

        if (hearingAidRepository.findById(productcode).isEmpty())
            throw new IndexOutOfBoundsException(String.format("Hearing aid with id %s was not found", productcode));

        HearingAid hearingAid = hearingAidRepository.findById(productcode).get();

        receipt.addHearingAid(hearingAid);
        receiptRepository.save(receipt);

        return ResponseEntity.ok("Hearing aid added to receipt");
    }

    public ResponseEntity<Object> removeHearingAid(Long receiptId, String productcode) {
        if (receiptRepository.findById(receiptId).isEmpty())
            throw new IndexOutOfBoundsException(String.format("Receipt with id %d was not found", receiptId));

        Receipt receipt = receiptRepository.findById(receiptId).get();

        if (hearingAidRepository.findById(productcode).isEmpty())
            throw new IndexOutOfBoundsException(String.format("Earpiece with id %s was not found", productcode));

        HearingAid hearingAidToRemove = receipt.getHearingAidList().stream().filter((a) -> a.getProductcode().equals(productcode)).findAny().get();

        receipt.removeHearingAid(hearingAidToRemove);
        receiptRepository.save(receipt);

        return ResponseEntity.ok("Hearing aid removed from receipt");
    }

    public Receipt toReceipt(InputReceiptDto dto) {
        var receipt = new Receipt();

        receipt.setId(dto.getId());
        receipt.setSaleDate(dto.getSaleDate());
        receipt.setCustomer(dto.getCustomer());

        return receipt;
    }

    // Dit is de vertaalmethode van Receipt naar ReceiptDto
    public ReturnReceiptDto fromReceipt(Receipt receipt) {
        ReturnReceiptDto dto = new ReturnReceiptDto();
        Optional<Customer> foundCustomer = Optional.ofNullable(receipt.getCustomer());
        Optional<List> foundEarPieceList = Optional.ofNullable(receipt.getEarPieceList());
        Optional<List> foundHearingAidList = Optional.ofNullable(receipt.getHearingAidList());

        dto.setId(receipt.getId());
        dto.setSaleDate(receipt.getSaleDate());
        if (foundCustomer.isPresent()) {
            dto.setCustomerDto(receipt.getCustomer());
        }
        if (foundEarPieceList.isPresent()) {
            dto.setEarPieceDtoList(receipt.getEarPieceList());
        }
        if (foundHearingAidList.isPresent()) {
            dto.setHearingAidDtoList(receipt.getHearingAidList());
        }
        return dto;
    }
}
