package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByNumber(String number);           // поиск по номеру карты
    List<Card> findByStatus(CardStatus status);           // поиск по статусу (enum)
    List<Card> findByOwner(User owner);                   // поиск всех карт пользователя
    List<Card> findByOwnerAndStatus(User owner, CardStatus status); // все активные карты юзера
}
