package nl.novi.EindopdrachtBackend.controllers;

import jakarta.validation.Valid;
import nl.novi.EindopdrachtBackend.dtos.IdInputDto;
import nl.novi.EindopdrachtBackend.dtos.InputReceiptDto;
import nl.novi.EindopdrachtBackend.dtos.ProductcodeInputDto;
import nl.novi.EindopdrachtBackend.dtos.ReturnReceiptDto;
import nl.novi.EindopdrachtBackend.models.Receipt;
import nl.novi.EindopdrachtBackend.services.CustomerService;
import nl.novi.EindopdrachtBackend.services.EarPieceService;
import nl.novi.EindopdrachtBackend.services.HearingAidService;
import nl.novi.EindopdrachtBackend.services.ReceiptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/receipts")
public class ReceiptController {
    private final ReceiptService receiptService;
    private final EarPieceService earPieceService;
    private final CustomerService customerService;
    private final HearingAidService hearingAidService;

    public ReceiptController(CustomerService customerService,
                             EarPieceService earPieceService,
                             HearingAidService hearingAidService,
                             ReceiptService receiptService) {
        this.customerService = customerService;
        this.earPieceService = earPieceService;
        this.hearingAidService = hearingAidService;
        this.receiptService = receiptService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<ReturnReceiptDto>> getReceipts() {

        List<ReturnReceiptDto> receiptDtos = receiptService.getAllReceipts();

        return ResponseEntity.ok().body(receiptDtos);
    }

    @GetMapping(value = "/{receiptId}")
    public ResponseEntity<ReturnReceiptDto> getReceipt(@PathVariable("receiptId") Long receiptId) {

        ReturnReceiptDto optionalReceipt = receiptService.getReceipt(receiptId);

        return ResponseEntity.ok().body(optionalReceipt);
    }

    @PostMapping(value = "/{customerId}")
    public ResponseEntity<Object> createReceipt(@PathVariable("customerId") Long customerId, @RequestBody InputReceiptDto dto) {
        Long newReceiptId = receiptService.createReceipt(dto);
        receiptService.addCustomer(newReceiptId, customerId);
        customerService.addReceipt(customerId, newReceiptId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{receiptId}")
                .buildAndExpand(newReceiptId).toUri();

        return ResponseEntity.created(location).body(String.format("New receipt created with id %d", newReceiptId));
    }

    @PutMapping(value = "/{receiptId}")
    public ResponseEntity<Optional<Receipt>> updateReceipt(@PathVariable("receiptId") Long receiptId, @RequestBody InputReceiptDto dto) {
        Optional<Receipt> updatedReceipt = receiptService.updateReceipt(receiptId, dto);
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
