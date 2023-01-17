package nl.novi.EindopdrachtBackend.services;

import nl.novi.EindopdrachtBackend.dtos.HearingAidDto;
import nl.novi.EindopdrachtBackend.exceptions.DuplicateRecordException;
import nl.novi.EindopdrachtBackend.exceptions.IndexOutOfBoundsException;
import nl.novi.EindopdrachtBackend.models.HearingAid;
import nl.novi.EindopdrachtBackend.repositories.HearingAidRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HearingAidService {

    private final HearingAidRepository hearingAidRepository;

    public HearingAidService(HearingAidRepository hearingAidRepository) {
        this.hearingAidRepository = hearingAidRepository;
    }

    public List<HearingAidDto> getAllHearingAids() {
        List<HearingAidDto> collection = new ArrayList<>();
        List<HearingAid> list = hearingAidRepository.findAll();
        for (HearingAid hearingAid : list) {
            collection.add(fromHearingAid(hearingAid));
        }
        return collection;
    }

    public HearingAidDto getHearingAid(String productcode) {
        Optional<HearingAid> hearingAid = hearingAidRepository.findById(productcode);
        if (hearingAid.isEmpty())
            throw new IndexOutOfBoundsException(String.format("Productcode %s was not found", productcode));

        return fromHearingAid(hearingAid.get());
    }

    public String createHearingAid(HearingAidDto hearingAidDto) {
        String productcode = hearingAidDto.getProductcode();
        Optional<HearingAid> hearingAid = hearingAidRepository.findById(productcode);

        if (hearingAid.isPresent())
            throw new DuplicateRecordException(String.format("A hearing aid with productcode %s already exists", productcode));

        HearingAid savedHearingAid = hearingAidRepository.save(toHearingAid(hearingAidDto));
        return savedHearingAid.getProductcode();
    }

    public HearingAidDto updateHearingAid(String productcode, HearingAidDto hearingAidDto) {
        Optional<HearingAid> hearingAid = hearingAidRepository.findById(productcode);
        if (hearingAid.isEmpty())
            throw new IndexOutOfBoundsException(String.format("Hearing aid with id %s was not found", productcode));

        hearingAidRepository.save(toHearingAid(hearingAidDto));
        return hearingAidDto;
    }

    public ResponseEntity<Object> deleteHearingAid(String productcode) {
        Optional<HearingAid> hearingAid = hearingAidRepository.findById(productcode);
        if (hearingAid.isEmpty())
            throw new IndexOutOfBoundsException(String.format("Hearing aid with id %s was not found", productcode));

        hearingAidRepository.deleteById(productcode);
        return ResponseEntity.ok("Hearing aid removed from database");
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

    // Dit is de vertaalmethode van HearingAid naar HearingAidDto
    public HearingAidDto fromHearingAid(HearingAid hearingAid) {
        HearingAidDto dto = new HearingAidDto();

        dto.setProductcode(hearingAid.getProductcode());
        dto.setBrand(hearingAid.getBrand());
        dto.setType(hearingAid.getType());
        dto.setColour(hearingAid.getColour());
        dto.setPrice(hearingAid.getPrice());

        return dto;
    }
}
