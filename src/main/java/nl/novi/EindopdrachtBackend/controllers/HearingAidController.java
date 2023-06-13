package nl.novi.EindopdrachtBackend.controllers;

import nl.novi.EindopdrachtBackend.dtos.HearingAidDto;
import nl.novi.EindopdrachtBackend.services.HearingAidService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

        String newProductcode = hearingAidService.createHearingAid(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{productcode}")
                .buildAndExpand(newProductcode).toUri();

        return ResponseEntity.created(location).body(dto);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<HearingAidDto>> getHearingAids() {

        List<HearingAidDto> hearingAidDtos = hearingAidService.getAllHearingAids();

        return ResponseEntity.ok().body(hearingAidDtos);
    }

    @GetMapping(value = "/{productcode}")
    public ResponseEntity<HearingAidDto> getHearingAid(@PathVariable("productcode") String productcode) {

        HearingAidDto optionalHearingAid = hearingAidService.getHearingAid(productcode);

        return ResponseEntity.ok().body(optionalHearingAid);
    }

    @PutMapping(value = "/{productcode}")
    public ResponseEntity<HearingAidDto> updateHearingAid(@PathVariable("productcode") String productcode, @RequestBody HearingAidDto dto) {

        hearingAidService.updateHearingAid(productcode, dto);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/{productcode}")
    public ResponseEntity<Object> deleteHearingAid(@PathVariable("productcode") String productcode) {

        hearingAidService.deleteHearingAid(productcode);

        return ResponseEntity.ok("Hearing aid with productcode " + productcode + " was removed from the database");
    }
}
