package nl.novi.EindopdrachtBackend.dtos;

import jakarta.validation.constraints.NotNull;

public class ProductcodeInputDto {
    @NotNull(message = "Productcode is required")
    public String productcode;

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }
}
