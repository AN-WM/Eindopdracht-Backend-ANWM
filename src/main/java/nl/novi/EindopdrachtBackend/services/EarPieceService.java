package nl.novi.EindopdrachtBackend.services;

import nl.novi.EindopdrachtBackend.dtos.EarPieceDto;
import nl.novi.EindopdrachtBackend.exceptions.*;
import nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException;
import nl.novi.EindopdrachtBackend.models.EarPiece;
import nl.novi.EindopdrachtBackend.repositories.EarPieceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EarPieceService {

    private EarPieceRepository earPieceRepository;

    public EarPieceService(EarPieceRepository earPieceRepository) {
        this.earPieceRepository = earPieceRepository;
    }

    public List<EarPieceDto> getAllEarpieces() {
        List<EarPieceDto> collection = new ArrayList<>();
        List<EarPiece> list = earPieceRepository.findAll();
        for (EarPiece earPiece : list) {
            collection.add(fromEarPiece(earPiece));
        }
        return collection;
    }

    public EarPieceDto getEarPiece(Long id) {
        Optional<EarPiece> earPiece = earPieceRepository.findById(id);
        if (earPiece.isEmpty())
            throw new IndexOutOfBoundsException(String.format("ID %d was not found", id));

        return fromEarPiece(earPiece.get());
    }

    public Long saveEarPiece(EarPieceDto earPieceDto) {
        Long id = earPieceDto.getId();
        Optional<EarPiece> earPiece = earPieceRepository.findById(id);

        if (earPiece.isPresent())
            throw new DuplicateRecordException(String.format("An earpiece with id %s already exists", id));

        EarPiece savedEarPiece = earPieceRepository.save(toEarPiece(earPieceDto));
        return savedEarPiece.getId();
    }

    public EarPieceDto updateEarPiece(EarPieceDto earPieceDto) {
        Long id = earPieceDto.getId();
        Optional<EarPiece> earPiece = earPieceRepository.findById(id);
        if (earPiece.isEmpty())
            throw new IndexOutOfBoundsException(String.format("Earpiece with id %d was not found", id));

        earPieceRepository.save(toEarPiece(earPieceDto));
        return earPieceDto;
    }

    public ResponseEntity<Object> deleteEarPiece(Long id) {
        Optional<EarPiece> earPiece = earPieceRepository.findById(id);
        if (earPiece.isEmpty())
            throw new IndexOutOfBoundsException(String.format("Earpiece with id %d was not found", id));

        earPieceRepository.deleteById(id);
        return ResponseEntity.ok("Earpiece removed from database");
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
    public EarPieceDto fromEarPiece(EarPiece earPiece) {
        EarPieceDto dto = new EarPieceDto();

        dto.setId(earPiece.getId());
        dto.setType(earPiece.getType());
        dto.setColour(earPiece.getColour());
        dto.setSize(earPiece.getSize());
        dto.setPrice(earPiece.getPrice());

        return dto;
    }
}
