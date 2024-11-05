package dev.gest.invest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddLineDto {

    @NotBlank(message = "Asset is required")
    private String asset_name;

    @NotNull(message = "Number is required")
    private double asset_number;

    @NotNull(message = "Price is required")
    private double price;

    @NotNull(message = "Fees are required")
    private double fees;

    @NotBlank(message = "Date is required")
    private String date;

    public AddLineDto(String asset_name, double asset_number, double price, double fees, String date) {
        this.asset_name = asset_name;
        this.asset_number = asset_number;
        this.price = price;
        this.fees = fees;
        this.date = date;
    }
}
