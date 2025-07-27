package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    Page<Card> findByOwnerUsername(String username, Pageable pageable);
    List<Card> findByOwnerUsername(String username);
}

