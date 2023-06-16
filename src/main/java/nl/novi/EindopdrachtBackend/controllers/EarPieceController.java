package nl.novi.EindopdrachtBackend.controllers;

import nl.novi.EindopdrachtBackend.dtos.EarPieceDto;
import nl.novi.EindopdrachtBackend.services.EarPieceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/earpieces")
public class EarPieceController {
    private final EarPieceService earpieceService;

    public EarPieceController(EarPieceService earpieceService) {
        this.earpieceService = earpieceService;
    }

    @PostMapping(value = "")
    public ResponseEntity<EarPieceDto> createEarPiece(@RequestBody EarPieceDto dto) {;
        return earpieceService.createEarPiece(dto);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<EarPieceDto>> getEarPieces() {
        return earpieceService.getAllEarpieces();
    }

    @GetMapping(value = "/{earPieceId}")
    public ResponseEntity<EarPieceDto> getEarPiece(@PathVariable("earPieceId") Long earPieceId) {
        return earpieceService.getEarPiece(earPieceId);
    }

    @PutMapping(value = "/{earPieceId}")
    public ResponseEntity<EarPieceDto> updateEarPiece(@PathVariable("earPieceId") Long earPieceId,
                                                      @RequestBody EarPieceDto dto) {
        return earpieceService.updateEarPiece(earPieceId, dto);
    }

    @DeleteMapping(value = "/{earPieceId}")
    public ResponseEntity<Object> deleteEarPiece(@PathVariable("earPieceId") Long earPieceId) {
        return earpieceService.deleteEarPiece(earPieceId);
    }
}
