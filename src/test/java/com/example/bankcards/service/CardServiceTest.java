package com.example.bankcards.service;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.dto.CreateCardDto;
import com.example.bankcards.dto.TransferRequestDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardServiceTest {

    @Mock
    CardRepository cardRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    CardService cardService;

    User user;
    Card card;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setUsername("alex12");

        card = new Card();
        card.setId(1L);
        card.setNumber("1111222233334444");
        card.setBalance(1000.0);
        card.setExpireAt(LocalDate.now().plusYears(1));
        card.setStatus(CardStatus.ACTIVE.name());
        card.setOwner(user);
    }

    @Test
    void create_shouldSaveCard() {
        CreateCardDto dto = new CreateCardDto(LocalDate.now().plusYears(1).toString(), 500.0, "alex12");
        when(userRepository.findByUsername("alex12")).thenReturn(Optional.of(user));
        when(cardRepository.save(any(Card.class))).thenAnswer(inv -> {
            Card saved = inv.getArgument(0, Card.class);
            saved.setId(2L);
            return saved;
        });
        CardDto result = cardService.create(dto, "alex12");
        assertEquals("alex12", result.getOwnerUsername());
        assertEquals(500.0, result.getBalance());
    }

    @Test
    void getById_shouldReturnCardDto() {
        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
        CardDto result = cardService.getById(1L);
        assertEquals("alex12", result.getOwnerUsername());
    }

    @Test
    void transfer_shouldTransferIfValid() {
        Card cardFrom = new Card(); cardFrom.setId(1L); cardFrom.setOwner(user); cardFrom.setBalance(500.0);
        Card cardTo = new Card(); cardTo.setId(2L); cardTo.setOwner(user); cardTo.setBalance(0.0);

        when(cardRepository.findById(1L)).thenReturn(Optional.of(cardFrom));
        when(cardRepository.findById(2L)).thenReturn(Optional.of(cardTo));

        TransferRequestDto dto = new TransferRequestDto(1L, 2L, 100.0);

        cardService.transfer(dto, "alex12");
        assertEquals(400.0, cardFrom.getBalance());
        assertEquals(100.0, cardTo.getBalance());
        verify(cardRepository, times(1)).save(cardFrom);
        verify(cardRepository, times(1)).save(cardTo);
    }
}
