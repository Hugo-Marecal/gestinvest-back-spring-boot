package dev.gest.invest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddLineDto {

    @NotBlank(message = "Le nom de l'actif est requis")
    private String asset_name;

    @NotBlank(message = "La quantité est requise")
    private String asset_number;

    @NotBlank(message = "Le prix est requis")
    private String price;

    @NotBlank(message = "Les frais sont requis")
    private String fees;

    @NotBlank(message = "La date est requise")
    private String date;

    public AddLineDto(String asset_name, String asset_number, String price, String fees, String date) {
        this.asset_name = asset_name;
        this.asset_number = asset_number;
        this.price = price;
        this.fees = fees;
        this.date = date;
    }
}
