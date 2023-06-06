package nl.novi.EindopdrachtBackend.dtos;

import nl.novi.EindopdrachtBackend.models.Customer;
import nl.novi.EindopdrachtBackend.models.EarPiece;
import nl.novi.EindopdrachtBackend.models.HearingAid;
import nl.novi.EindopdrachtBackend.services.CustomerService;
import nl.novi.EindopdrachtBackend.services.EarPieceService;
import nl.novi.EindopdrachtBackend.services.HearingAidService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReturnReceiptDto {

    private HearingAidService hearingAidService;

    public Long id;
    public Date saleDate;
    public CustomerDto customerDto;
    private List<EarPieceDto> earPieceDtoList = new ArrayList<>();
    private List<HearingAidDto> hearingAidDtoList = new ArrayList<>();

    public ReturnReceiptDto() {
    }

    public ReturnReceiptDto(long id, Date saleDate) {
        this.id = id;
        this.saleDate = saleDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public CustomerDto getCustomerDto() { return this.customerDto; }

    public void setCustomerDto(Customer customer) {
        this.customerDto = CustomerService.fromCustomer(customer);
    }

    public List<HearingAidDto> getHearingAidDtoList() { return this.hearingAidDtoList; }

    public void setHearingAidDtoList(List<HearingAid> hearingAidList) {
        for (HearingAid hearingAid : hearingAidList) {
            hearingAidDtoList.add(HearingAidService.fromHearingAid(hearingAid));
        }
    }

    public List<EarPieceDto> getEarPieceDtoList() { return this.earPieceDtoList; }

    public void setEarPieceDtoList(List<EarPiece> earPieceList) {
        for (EarPiece earpiece : earPieceList) {
            earPieceDtoList.add(EarPieceService.fromEarPiece(earpiece));
        }
    }
}
