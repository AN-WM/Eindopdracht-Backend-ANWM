package nl.novi.EindopdrachtBackend.dtos;

import nl.novi.EindopdrachtBackend.models.Customer;
import nl.novi.EindopdrachtBackend.models.EarPiece;
import nl.novi.EindopdrachtBackend.models.HearingAid;
import nl.novi.EindopdrachtBackend.services.CustomerService;
import nl.novi.EindopdrachtBackend.services.EarPieceService;
import nl.novi.EindopdrachtBackend.services.HearingAidService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReturnReceiptDto {

    public Long id;
    public LocalDate saleDate;
    public CustomerDto customerDto;
    private final List<EarPieceDto> earPieceDtoList = new ArrayList<>();
    private final List<HearingAidDto> hearingAidDtoList = new ArrayList<>();

    public ReturnReceiptDto() {
    }

    public ReturnReceiptDto(long id, LocalDate saleDate) {
        this.id = id;
        this.saleDate = saleDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public CustomerDto getCustomerDto() {
        return this.customerDto;
    }

    public void setCustomerDto(Customer customer) {
        this.customerDto = CustomerService.fromCustomer(customer);
    }

    public List<HearingAidDto> getHearingAidDtoList() {
        return this.hearingAidDtoList;
    }

    public void setHearingAidDtoList(List<HearingAid> hearingAidList) {
        for (HearingAid hearingAid : hearingAidList) {
            hearingAidDtoList.add(HearingAidService.fromHearingAid(hearingAid));
        }
    }

    public List<EarPieceDto> getEarPieceDtoList() {
        return this.earPieceDtoList;
    }

    public void setEarPieceDtoList(List<EarPiece> earPieceList) {
        for (EarPiece earpiece : earPieceList) {
            earPieceDtoList.add(EarPieceService.fromEarPiece(earpiece));
        }
    }
}
