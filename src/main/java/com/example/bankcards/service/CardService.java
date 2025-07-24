package com.example.bankcards.service;

import com.example.bankcards.entity.Card;
import com.example.bankcards.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }


    public List<Card> getCardsByUserId(Long userId) {
        return cardRepository.findByOwnerId(userId);
    }


    public Optional<Card> getCardById(Long id) {
        return cardRepository.findById(id);
    }


    public Card createCard(Card card) {
        return cardRepository.save(card);
    }


    public Card updateCard(Card card) {
        return cardRepository.save(card);
    }


    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }
}
