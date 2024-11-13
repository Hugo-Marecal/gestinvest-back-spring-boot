package dev.gest.invest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddLineDto {

    @NotBlank(message = "Asset is required")
    private String asset_name;

    @NotBlank(message = "Quantity of asset is required")
    private String asset_number;

    @NotBlank(message = "Price is required")
    private String price;

    @NotBlank(message = "Fees are required")
    private String fees;

    @NotBlank(message = "Date is required")
    private String date;

    public AddLineDto(String asset_name, String asset_number, String price, String fees, String date) {
        this.asset_name = asset_name;
        this.asset_number = asset_number;
        this.price = price;
        this.fees = fees;
        this.date = date;
    }
}
