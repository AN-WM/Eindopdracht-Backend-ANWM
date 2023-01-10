package nl.novi.EindopdrachtBackend.dtos;

import jakarta.validation.constraints.*;

public class EarPieceDto {
    @NotBlank
    private String type;
    private String colour;
    private String size;
    @Positive
    private Double price;

    private EarPieceDto() {
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
