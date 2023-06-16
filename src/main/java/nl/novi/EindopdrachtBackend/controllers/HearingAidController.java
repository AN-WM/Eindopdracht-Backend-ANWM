package nl.novi.EindopdrachtBackend.controllers;

import nl.novi.EindopdrachtBackend.dtos.HearingAidDto;
import nl.novi.EindopdrachtBackend.services.HearingAidService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/hearingaids")
public class HearingAidController {
    private final HearingAidService hearingAidService;

    public HearingAidController(HearingAidService hearingAidService) {
        this.hearingAidService = hearingAidService;
    }

    @PostMapping(value = "")
    public ResponseEntity<HearingAidDto> createHearingAid(@RequestBody HearingAidDto dto) {;
        return hearingAidService.createHearingAid(dto);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<HearingAidDto>> getHearingAids() {
        return hearingAidService.getAllHearingAids();
    }

    @GetMapping(value = "/{productcode}")
    public ResponseEntity<HearingAidDto> getHearingAid(@PathVariable("productcode") String productcode) {
        return hearingAidService.getHearingAid(productcode);
    }

    @PutMapping(value = "/{productcode}")
    public ResponseEntity<HearingAidDto> updateHearingAid(@PathVariable("productcode") String productcode,
                                                          @RequestBody HearingAidDto dto) {
        return hearingAidService.updateHearingAid(productcode, dto);
    }

    @DeleteMapping(value = "/{productcode}")
    public ResponseEntity<Object> deleteHearingAid(@PathVariable("productcode") String productcode) {
        return hearingAidService.deleteHearingAid(productcode);
    }
}
