package nl.novi.EindopdrachtBackend.services;

import nl.novi.EindopdrachtBackend.dtos.EarPieceDto;
import nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException;
import nl.novi.EindopdrachtBackend.models.EarPiece;
import nl.novi.EindopdrachtBackend.models.Receipt;
import nl.novi.EindopdrachtBackend.repositories.EarPieceRepository;
import nl.novi.EindopdrachtBackend.repositories.ReceiptRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Service
public class EarPieceService {

    private final EarPieceRepository earPieceRepository;
    private final ReceiptRepository receiptRepository;

    public EarPieceService(EarPieceRepository earPieceRepository,
                           ReceiptRepository receiptRepository) {
        this.earPieceRepository = earPieceRepository;
        this.receiptRepository = receiptRepository;
    }

    public ResponseEntity<EarPieceDto> createEarPiece(EarPieceDto earPieceDto) {
        EarPiece savedEarPiece = earPieceRepository.save(toEarPiece(earPieceDto));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{earPieceId}")
                .buildAndExpand(savedEarPiece.getId()).toUri();

        return ResponseEntity.created(location).body(fromEarPiece(savedEarPiece));
    }

    public ResponseEntity<List<EarPieceDto>> getAllEarpieces() {
        List<EarPieceDto> collection = new ArrayList<>();
        List<EarPiece> list = earPieceRepository.findAll();
        for (EarPiece earPiece : list) {
            collection.add(fromEarPiece(earPiece));
        }

        return ResponseEntity.ok().body(collection);
    }

    public ResponseEntity<EarPieceDto> getEarPiece(Long id) {
        Optional<EarPiece> earPiece = earPieceRepository.findById(id);
        if (earPiece.isEmpty())
            throw new IndexOutOfBoundsException(String.format("ID %d was not found", id));

        return ResponseEntity.ok().body(fromEarPiece(earPiece.get()));
    }

    public ResponseEntity<EarPieceDto> updateEarPiece(Long earPieceId, EarPieceDto earPieceDto) {
        Optional<EarPiece> earPiece = earPieceRepository.findById(earPieceId);
        if (earPiece.isEmpty())
            throw new IndexOutOfBoundsException(String.format("Earpiece with id %d was not found", earPieceId));

        earPieceRepository.save(toEarPiece(earPieceDto));
        return ResponseEntity.ok(earPieceDto);
    }

    public ResponseEntity<Object> deleteEarPiece(Long id) {
        if (earPieceRepository.findById(id).isEmpty())
            throw new IndexOutOfBoundsException(String.format("Earpiece with id %d was not found", id));

        EarPiece earPiece = earPieceRepository.findById(id).get();

        Receipt boundReceipt = earPiece.getReceipt();
        if (boundReceipt != null) {
            boundReceipt.removeEarPiece(earPiece);
            receiptRepository.save(boundReceipt);
        }

        earPieceRepository.deleteById(id);

        return ResponseEntity.ok("Earpiece with ID " + id + " was removed from the database");
    }

    public EarPiece toEarPiece(EarPieceDto dto) {
        var earPiece = new EarPiece();

        earPiece.setId(dto.getId());
        earPiece.setType(dto.getType());
        earPiece.setColour(dto.getColour());
        earPiece.setSize(dto.getSize());
        earPiece.setPrice(dto.getPrice());

        return earPiece;
    }

    // Dit is de vertaalmethode van EarPiece naar EarPieceDto
    public static EarPieceDto fromEarPiece(EarPiece earPiece) {
        EarPieceDto dto = new EarPieceDto();

        dto.setId(earPiece.getId());
        dto.setType(earPiece.getType());
        dto.setColour(earPiece.getColour());
        dto.setSize(earPiece.getSize());
        dto.setPrice(earPiece.getPrice());

        return dto;
    }
}
