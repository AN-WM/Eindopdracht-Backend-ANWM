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
//    public Customer customer;
    public CustomerDto customerDto;
//    private List<EarPiece> earPieceList;
    private List<EarPieceDto> earPieceDtoList = new ArrayList<>();
    private List<HearingAidDto> hearingAidDtoList = new ArrayList<>();

    public ReturnReceiptDto() {
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

    public CustomerDto getCustomerDto(Customer customer) {
        return CustomerService.fromCustomer(customer);
    }

    public void setCustomerDto(Customer customer) {
        this.customerDto = CustomerService.fromCustomer(customer);
    }

    public List<HearingAidDto> getHearingAidDtoList(List<HearingAid> hearingAidList) {
        List<HearingAidDto> dtoList = null;

        for (HearingAid hearingAid : hearingAidList) {
            dtoList.add(HearingAidService.fromHearingAid(hearingAid));
        }

        return dtoList;
    }

    public void setHearingAidDtoList(List<HearingAid> hearingAidList) {
       // hearingAidDtoList.clear();

        for (HearingAid hearingAid : hearingAidList) {
            hearingAidDtoList.add(HearingAidService.fromHearingAid(hearingAid));
        }
    }

    public List<EarPieceDto> getEarPieceDtoList(List <EarPiece> earPieceList) {
        List<EarPieceDto> dtoList = new ArrayList<>();

        for (EarPiece earPiece : earPieceList) {
            dtoList.add(EarPieceService.fromEarPiece(earPiece));
        }

        return dtoList;
    }

    public void setEarPieceDtoList(List<EarPiece> earPieceList) {
//        earPieceDtoList = null;

        for (EarPiece earpiece : earPieceList) {
            earPieceDtoList.add(EarPieceService.fromEarPiece(earpiece));
        }
    }

//    public Customer getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }
//
//    public List<HearingAid> getHearingAidList() {
//        return hearingAidList;
//    }
//
//    public void setHearingAidList(List<HearingAid> hearingAidList) {
//        this.hearingAidList = hearingAidList;
//    }
//
//    public List<EarPiece> getEarPieceList() {
//        return earPieceList;
//    }
//
//    public void setEarPieceList(List<EarPiece> earPieceList) {
//        this.earPieceList = earPieceList;
//    }
}
