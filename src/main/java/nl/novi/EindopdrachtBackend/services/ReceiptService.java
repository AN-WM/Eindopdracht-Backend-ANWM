package nl.novi.EindopdrachtBackend.services;

import nl.novi.EindopdrachtBackend.dtos.ReceiptDto;
import nl.novi.EindopdrachtBackend.exceptions.*;
import nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException;
import nl.novi.EindopdrachtBackend.models.*;
import nl.novi.EindopdrachtBackend.repositories.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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


    public List<ReceiptDto> getAllReceipts() {
        List<ReceiptDto> collection = new ArrayList<>();
        List<Receipt> list = receiptRepository.findAll();
        for (Receipt receipt : list) {
            collection.add(fromReceipt(receipt));
        }
        return collection;
    }

    public ReceiptDto getReceipt(Long id) {
        Optional<Receipt> receipt = receiptRepository.findById(id);
        if (receipt.isEmpty())
            throw new IndexOutOfBoundsException(String.format("ID %d was not found", id));

        return fromReceipt(receipt.get());
    }

    public Long createReceipt(ReceiptDto receiptDto) {
        Receipt savedReceipt = receiptRepository.save(toReceipt(receiptDto));
        return savedReceipt.getId();
    }

    public ReceiptDto updateReceipt(Long receiptId, ReceiptDto receiptDto) {
        Optional<Receipt> receipt = receiptRepository.findById(receiptId);
        if (receipt.isEmpty())
            throw new IndexOutOfBoundsException(String.format("Receipt with id %d was not found", receiptId));

        receiptRepository.save(toReceipt(receiptDto));
        return receiptDto;
    }

    public ResponseEntity<Object> deleteReceipt(Long id) {
        Optional<Receipt> receipt = receiptRepository.findById(id);
        if (receipt.isEmpty())
            throw new IndexOutOfBoundsException(String.format("Receipt with id %d was not found", id));

        receiptRepository.deleteById(id);
        return ResponseEntity.ok(String.format("Receipt with id %d was removed from the database", id));
    }

    public ResponseEntity<Object> addCustomer(Long receiptId, Long customerId) {
        if (receiptRepository.findById(receiptId).isEmpty())
            throw new IndexOutOfBoundsException(String.format("Receipt with id %d was not found", receiptId));

        Receipt receipt = receiptRepository.findById(receiptId).get();

        if (customerRepository.findById(customerId).isEmpty())
            throw new IndexOutOfBoundsException(String.format("Customer with id %d was not found", customerId));

        Customer customer = customerRepository.findById(customerId).get();

        receipt.setCustomer(customer);
        receiptRepository.save(receipt);

        return ResponseEntity.ok("Customer added to receipt");
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

    public Receipt toReceipt(ReceiptDto dto) {
        var receipt = new Receipt();

        receipt.setId(dto.getId());
        receipt.setSaleDate(dto.getSaleDate());

        return receipt;
    }

    // Dit is de vertaalmethode van Receipt naar ReceiptDto
    public ReceiptDto fromReceipt(Receipt receipt) {
        ReceiptDto dto = new ReceiptDto();

        dto.setId(receipt.getId());
        dto.setSaleDate(receipt.getSaleDate());

        return dto;
    }
}
