package nl.novi.EindopdrachtBackend.services;

import nl.novi.EindopdrachtBackend.dtos.HearingAidDto;
import nl.novi.EindopdrachtBackend.exceptions.DuplicateRecordException;
import nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException;
import nl.novi.EindopdrachtBackend.models.EarPiece;
import nl.novi.EindopdrachtBackend.models.HearingAid;
import nl.novi.EindopdrachtBackend.models.Receipt;
import nl.novi.EindopdrachtBackend.repositories.EarPieceRepository;
import nl.novi.EindopdrachtBackend.repositories.HearingAidRepository;
import nl.novi.EindopdrachtBackend.repositories.ReceiptRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Service
public class HearingAidService {

    private final HearingAidRepository hearingAidRepository;
    private final EarPieceRepository earPieceRepository;
    private final ReceiptRepository receiptRepository;

    public HearingAidService(HearingAidRepository hearingAidRepository,
                             EarPieceRepository earPieceRepository,
                             ReceiptRepository receiptRepository) {
        this.hearingAidRepository = hearingAidRepository;
        this.earPieceRepository = earPieceRepository;
        this.receiptRepository = receiptRepository;
    }

    public ResponseEntity<HearingAidDto> createHearingAid(HearingAidDto hearingAidDto) {
        String productcode = hearingAidDto.getProductcode();
        Optional<HearingAid> hearingAid = hearingAidRepository.findById(productcode);

        if (hearingAid.isPresent())
            throw new DuplicateRecordException(String.format("A hearing aid with productcode %s already exists", productcode));

        HearingAid savedHearingAid = hearingAidRepository.save(toHearingAid(hearingAidDto));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{productcode}")
                .buildAndExpand(savedHearingAid.getProductcode()).toUri();

        return ResponseEntity.created(location).body(fromHearingAid(savedHearingAid));
    }

    public ResponseEntity<List<HearingAidDto>> getAllHearingAids() {
        List<HearingAidDto> collection = new ArrayList<>();
        List<HearingAid> list = hearingAidRepository.findAll();

        for (HearingAid hearingAid : list) {
            collection.add(fromHearingAid(hearingAid));
        }

        return ResponseEntity.ok().body(collection);
    }

    public ResponseEntity<HearingAidDto> getHearingAid(String productcode) {
        Optional<HearingAid> hearingAid = hearingAidRepository.findById(productcode);

        if (hearingAid.isEmpty())
            throw new IndexOutOfBoundsException(String.format("Productcode %s was not found", productcode));

        return ResponseEntity.ok().body(fromHearingAid(hearingAid.get()));
    }


    public ResponseEntity<HearingAidDto> updateHearingAid(String productcode, HearingAidDto hearingAidDto) {
        Optional<HearingAid> hearingAid = hearingAidRepository.findById(productcode);
        if (hearingAid.isEmpty())
            throw new IndexOutOfBoundsException(String.format("Hearing aid with id %s was not found", productcode));

        hearingAidRepository.save(toHearingAid(hearingAidDto));

        return ResponseEntity.ok(fromHearingAid(hearingAid.get()));
    }

    public ResponseEntity<Object> deleteHearingAid(String productcode) {

        if (hearingAidRepository.findById(productcode).isEmpty())
            throw new IndexOutOfBoundsException(
                    String.format("Hearing aid with productcode %s was not found", productcode));

        HearingAid hearingAid = hearingAidRepository.findById(productcode).get();

        EarPiece boundEarPiece = hearingAid.getEarPiece();
        if (boundEarPiece != null) {
            boundEarPiece.setHearingAid(null);
            earPieceRepository.save(boundEarPiece);
        }

        Receipt boundReceipt = hearingAid.getReceipt();
        if (boundReceipt != null) {
            boundReceipt.removeHearingAid(hearingAid);
            receiptRepository.save(boundReceipt);
        }

        hearingAidRepository.deleteById(productcode);

        return ResponseEntity.ok(
                String.format("Hearing aid with productcode %s was removed from the database", productcode));
    }

    public HearingAid toHearingAid(HearingAidDto dto) {
        var hearingAid = new HearingAid();

        hearingAid.setProductcode(dto.getProductcode());
        hearingAid.setBrand(dto.getBrand());
        hearingAid.setType(dto.getType());
        hearingAid.setColour(dto.getColour());
        hearingAid.setPrice(dto.getPrice());

        return hearingAid;
    }

    public static HearingAidDto fromHearingAid(HearingAid hearingAid) {
        HearingAidDto dto = new HearingAidDto();

        dto.setProductcode(hearingAid.getProductcode());
        dto.setBrand(hearingAid.getBrand());
        dto.setType(hearingAid.getType());
        dto.setColour(hearingAid.getColour());
        dto.setPrice(hearingAid.getPrice());

        return dto;
    }
}
