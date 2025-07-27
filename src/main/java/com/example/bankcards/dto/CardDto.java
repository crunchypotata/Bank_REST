package com.example.bankcards.dto;

import lombok.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {
    private Long id;
    private String maskedNumber;
    private String status;
    private String expireAt;
    private Double balance;
    private String ownerUsername;
}

