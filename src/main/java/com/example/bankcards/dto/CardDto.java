package com.example.bankcards.dto;

public class CardDto {
    private Long id;
    private String maskedNumber;
    private String status;
    private String expireAt;
    private Double balance;
    private String ownerUsername;

    public CardDto() {}


    public CardDto(Long id, String maskedNumber, String status, String expireAt, Double balance, String ownerUsername) {
        this.id = id;
        this.maskedNumber = maskedNumber;
        this.status = status;
        this.expireAt = expireAt;
        this.balance = balance;
        this.ownerUsername = ownerUsername;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(String expireAt) {
        this.expireAt = expireAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaskedNumber() {
        return maskedNumber;
    }

    public void setMaskedNumber(String maskedNumber) {
        this.maskedNumber = maskedNumber;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

