package com.example.bankcards.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;
    private LocalDate expireAt;
    private String status;
    private Double balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // внешний ключ на пользователя
    private User owner;
}

