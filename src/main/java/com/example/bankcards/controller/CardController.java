package com.example.bankcards.controller;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.dto.CreateCardDto;
import com.example.bankcards.dto.TransferRequestDto;
import com.example.bankcards.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards")
public class CardController {
    private final CardService cardService;

    @GetMapping("/me/paged")
    public Page<CardDto> getMyCardsPaged(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return cardService.getByUsername(userDetails.getUsername(), pageable);
    }


    @PostMapping
    public CardDto createCard(@Valid @RequestBody CreateCardDto dto,
                              @AuthenticationPrincipal UserDetails userDetails) {
        return cardService.create(dto, userDetails.getUsername());
    }

    @GetMapping("/{id}")
    public CardDto getById(@PathVariable Long id) {
        return cardService.getById(id);
    }

    // user
    @PutMapping("/status")
    public CardDto requestCardBlock(@Valid @RequestBody CardDto dto,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        return cardService.userRequestCardBlock(dto, userDetails.getUsername());
    }

    @DeleteMapping("/{id}")
    public void deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/all")
    public List<CardDto> getAllCards() {
        return cardService.getAllCards();
    }

    // admin, set status of card
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/set-status")
    public CardDto adminUpdateCardStatus(@Valid @RequestParam Long cardId,
                                         @RequestParam String status) {
        return cardService.adminUpdateCardStatus(cardId, status);
    }

    @PostMapping("/transfer")
    public void transfer(@Valid @RequestBody TransferRequestDto dto,
                         @AuthenticationPrincipal UserDetails userDetails) {
        cardService.transfer(dto, userDetails.getUsername());
    }

}
