package com.example.bankcards.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "cards")
@Data
public class Card {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private String owner;
    private LocalDate expireAt;
    private String status;
    private Double balance;
}

