package nl.novi.EindopdrachtBackend.dtos;

import jakarta.validation.constraints.NotNull;

public class ProductcodeInputDto {
    @NotNull
    public String productcode;
}
