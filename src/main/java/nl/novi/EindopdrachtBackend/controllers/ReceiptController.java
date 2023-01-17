package nl.novi.EindopdrachtBackend.controllers;

import nl.novi.EindopdrachtBackend.dtos.ReceiptDto;
import nl.novi.EindopdrachtBackend.services.ReceiptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/receipts")
public class ReceiptController {
    private ReceiptService earpieceService;

    public ReceiptController(ReceiptService earpieceService) {
        this.earpieceService = earpieceService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<ReceiptDto>> getReceipts() {

        List<ReceiptDto> earpieceDtos = earpieceService.getAllReceipts();

        return ResponseEntity.ok().body(earpieceDtos);
    }

    @GetMapping(value = "/{receiptId}")
    public ResponseEntity<ReceiptDto> getReceipt(@PathVariable("receiptId") Long receiptId) {

        ReceiptDto optionalReceipt = earpieceService.getReceipt(receiptId);

        return ResponseEntity.ok().body(optionalReceipt);
    }

    @PostMapping(value = "")
    public ResponseEntity<ReceiptDto> createReceipt(@RequestBody ReceiptDto dto) {;

        Long newReceiptId = earpieceService.createReceipt(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{receiptId}")
                .buildAndExpand(newReceiptId).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/{receiptId}")
    public ResponseEntity<ReceiptDto> updateReceipt(@PathVariable("receiptId") Long receiptId, @RequestBody ReceiptDto dto) {

        earpieceService.updateReceipt(receiptId, dto);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{receiptId}")
    public ResponseEntity<Object> deleteReceipt(@PathVariable("receiptId") Long receiptId) {

        earpieceService.deleteReceipt(receiptId);

        return ResponseEntity.noContent().build();
    }
}
