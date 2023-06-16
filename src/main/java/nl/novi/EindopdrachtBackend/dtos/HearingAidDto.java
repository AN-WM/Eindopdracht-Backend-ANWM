package nl.novi.EindopdrachtBackend.dtos;

import jakarta.validation.constraints.*;

public class HearingAidDto {
    @NotBlank(message = "Productcode is required")
    private String productcode;
    private String brand;
    private String type;
    private String colour;
    @Positive
    private Double price;

    public HearingAidDto() {
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
