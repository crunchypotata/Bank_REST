package com.example.bankcards.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCardDto {

    @NotNull(message = "Expiration date is required")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Expiration date must be in format YYYY-MM-DD")
    private String expireAt;

    @NotNull(message = "Balance is required")
    @Min(value = 0, message = "Balance cannot be negative")
    private Double balance;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 30, message = "Username must be 3-30 chars")
    private String username;
}