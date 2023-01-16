package nl.novi.EindopdrachtBackend.services;

import nl.novi.EindopdrachtBackend.dtos.ReceiptDto;
import nl.novi.EindopdrachtBackend.exceptions.*;
import nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException;
import nl.novi.EindopdrachtBackend.models.Receipt;
import nl.novi.EindopdrachtBackend.repositories.ReceiptRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReceiptService {

    private final ReceiptRepository receiptRepository;

    public ReceiptService(ReceiptRepository receiptRepository) {
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

    public Long saveReceipt(ReceiptDto receiptDto) {
        Long id = receiptDto.getId();
        Optional<Receipt> receipt = receiptRepository.findById(id);

        if (receipt.isPresent())
            throw new DuplicateRecordException(String.format("A receipt with id %d already exists", id));

        Receipt savedReceipt = receiptRepository.save(toReceipt(receiptDto));
        return savedReceipt.getId();
    }

    public ReceiptDto updateReceipt(ReceiptDto receiptDto) {
        Long id = receiptDto.getId();
        Optional<Receipt> receipt = receiptRepository.findById(id);
        if (receipt.isEmpty())
            throw new IndexOutOfBoundsException(String.format("Receipt with id %d was not found", id));

        receiptRepository.save(toReceipt(receiptDto));
        return receiptDto;
    }

    public ResponseEntity<Object> deleteReceipt(Long id) {
        Optional<Receipt> receipt = receiptRepository.findById(id);
        if (receipt.isEmpty())
            throw new IndexOutOfBoundsException(String.format("Receipt with id %d was not found", id));

        receiptRepository.deleteById(id);
        return ResponseEntity.ok("Receipt removed from database");
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
