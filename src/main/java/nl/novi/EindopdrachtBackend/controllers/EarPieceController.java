package nl.novi.EindopdrachtBackend.controllers;

import nl.novi.EindopdrachtBackend.dtos.EarPieceDto;
import nl.novi.EindopdrachtBackend.services.EarPieceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/earpieces")
public class EarPieceController {
    private EarPieceService earpieceService;

    public EarPieceController(EarPieceService earpieceService) {
        this.earpieceService = earpieceService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<EarPieceDto>> getEarPieces() {

        List<EarPieceDto> earpieceDtos = earpieceService.getAllEarpieces();

        return ResponseEntity.ok().body(earpieceDtos);
    }

    @GetMapping(value = "/{earPieceId}")
    public ResponseEntity<EarPieceDto> getEarPiece(@PathVariable("earPieceId") Long earPieceId) {

        EarPieceDto optionalEarPiece = earpieceService.getEarPiece(earPieceId);

        return ResponseEntity.ok().body(optionalEarPiece);
    }

    @PostMapping(value = "")
    public ResponseEntity<EarPieceDto> createEarPiece(@RequestBody EarPieceDto dto) {;

        Long newEarPieceId = earpieceService.createEarPiece(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{earPieceId}")
                .buildAndExpand(newEarPieceId).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/{earPieceId}")
    public ResponseEntity<EarPieceDto> updateEarPiece(@PathVariable("earPieceId") Long earPieceId, @RequestBody EarPieceDto dto) {

        earpieceService.updateEarPiece(earPieceId, dto);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{earPieceId}")
    public ResponseEntity<Object> deleteEarPiece(@PathVariable("earPieceId") Long earPieceId) {

        earpieceService.deleteEarPiece(earPieceId);

        return ResponseEntity.noContent().build();
    }
}
