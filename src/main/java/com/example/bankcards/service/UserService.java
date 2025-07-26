package com.example.bankcards.service;

import com.example.bankcards.dto.UserDto;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    // Создание пользователя (регистрация)
    public UserDto create(User dto) {
        if (repository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("User with this username already exists");
        }
        if (repository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("User with this email already exists");
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        // Шифруем пароль!
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        // Здесь можно назначить роль по умолчанию
        User saved = repository.save(user);
        return toDto(saved);
    }

    // Получение пользователя по username
    public UserDto getByUsername(String username) {
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return toDto(user);
    }

    // Получение пользователя по id
    public UserDto getById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return toDto(user);
    }

    // Обновление пользователя (только ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto updateUser(UserDto dto) {
        User user = repository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getId()));
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        // другие поля при необходимости
        User updated = repository.save(user);
        return toDto(updated);
    }

    // Удаление пользователя (только ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        repository.delete(user);
    }

    // Получить текущего пользователя
    public UserDto getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    // Методы-конвертеры

    public UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getEmail());
    }
}
