package com.example.bankcards.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Getter
@Setter
public class TransferRequestDto {
    @NotNull(message = "Source card ID is required")
    private Long fromCardId;

    @NotNull(message = "Destination card ID is required")
    private Long toCardId;

    @NotNull(message = "Amount is required")
    @Min(value = 1, message = "Amount must be greater than 0")
    private Double amount;
}
