package nl.novi.EindopdrachtBackend.controllers;

import jakarta.validation.Valid;
import nl.novi.EindopdrachtBackend.dtos.IdInputDto;
import nl.novi.EindopdrachtBackend.dtos.ProductcodeInputDto;
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
    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<ReceiptDto>> getReceipts() {

        List<ReceiptDto> earpieceDtos = receiptService.getAllReceipts();

        return ResponseEntity.ok().body(earpieceDtos);
    }

    @GetMapping(value = "/{receiptId}")
    public ResponseEntity<ReceiptDto> getReceipt(@PathVariable("receiptId") Long receiptId) {

        ReceiptDto optionalReceipt = receiptService.getReceipt(receiptId);

        return ResponseEntity.ok().body(optionalReceipt);
    }

    @PostMapping(value = "/{customerId}")
    public ResponseEntity<Object> createReceipt(@PathVariable("customerId") Long customerId, @RequestBody ReceiptDto dto) {
        Long newReceiptId = receiptService.createReceipt(dto);
        receiptService.addCustomer(newReceiptId, customerId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{receiptId}")
                .buildAndExpand(newReceiptId).toUri();

        return ResponseEntity.created(location).body(String.format("New receipt created with id %d", newReceiptId));
    }

    @PutMapping(value = "/{receiptId}")
    public ResponseEntity<ReceiptDto> updateReceipt(@PathVariable("receiptId") Long receiptId, @RequestBody ReceiptDto dto) {
        ReceiptDto updatedReceipt = receiptService.updateReceipt(receiptId, dto);
        return ResponseEntity.ok(updatedReceipt);
    }

    @PutMapping("/{receiptId}/earpiece")
    public ResponseEntity<Object> assignEarPieceToReceipt(@PathVariable("receiptId") Long receiptId, @Valid @RequestBody IdInputDto earpieceId) {
        return receiptService.addEarPiece(receiptId, earpieceId.id);
    }

    @PutMapping("/{receiptId}/hearingaid")
    public ResponseEntity<Object> assignHearingAidToReceipt(@PathVariable("receiptId") Long receiptId, @Valid @RequestBody ProductcodeInputDto hearingAidId) {
        return receiptService.addHearingAid(receiptId, String.valueOf(hearingAidId.productcode));
    }

    @DeleteMapping(value = "/{receiptId}")
    public ResponseEntity<Object> deleteReceipt(@PathVariable("receiptId") Long receiptId) {
        return receiptService.deleteReceipt(receiptId);
    }

    @DeleteMapping(value = "/{receiptId}/earpiece")
    public ResponseEntity<Object> removeEarpieceFromReceipt(@PathVariable("receiptId") Long receiptId, @Valid @RequestBody IdInputDto earpieceId) {
        return receiptService.removeEarPiece(receiptId, earpieceId.id);
    }

    @DeleteMapping(value = "/{receiptId}/hearingaid")
    public ResponseEntity<Object> removeHearingAidFromReceipt(@PathVariable("receiptId") Long receiptId, @Valid @RequestBody ProductcodeInputDto hearingAidId) {
        return receiptService.removeHearingAid(receiptId, hearingAidId.productcode);
    }
}
