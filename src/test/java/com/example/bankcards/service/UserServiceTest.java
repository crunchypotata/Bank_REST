package com.example.bankcards.service;

import com.example.bankcards.dto.CreateUserDto;
import com.example.bankcards.dto.UserDto;
import com.example.bankcards.entity.User;
import com.example.bankcards.entity.UserRole;
import com.example.bankcards.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void create_NewUser_Success() {
        CreateUserDto createUserDto = new CreateUserDto("alex", "alex@mail.com", "password");

        when(userRepository.existsByUsername("alex")).thenReturn(false);
        when(userRepository.existsByEmail("alex@mail.com")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("hashedPass");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("alex");
        savedUser.setEmail("alex@mail.com");
        savedUser.setPassword("hashedPass");
        savedUser.setUserRole(UserRole.ROLE_USER);

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserDto result = userService.create(createUserDto);

        assertEquals("alex", result.getUsername());
        assertEquals("alex@mail.com", result.getEmail());
        assertEquals(1L, result.getId());
    }

    @Test
    void getByUsername_Found() {
        User user = new User();
        user.setId(2L);
        user.setUsername("test");
        user.setEmail("t@mail.com");
        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));

        UserDto dto = userService.getByUsername("test");

        assertEquals("test", dto.getUsername());
        assertEquals("t@mail.com", dto.getEmail());
    }

    @Test
    void getById_Found() {
        User user = new User();
        user.setId(3L);
        user.setUsername("x");
        user.setEmail("x@mail.com");
        when(userRepository.findById(3L)).thenReturn(Optional.of(user));

        UserDto dto = userService.getById(3L);

        assertEquals("x", dto.getUsername());
        assertEquals("x@mail.com", dto.getEmail());
    }

    @Test
    void toDto_ConvertsUser() {
        User user = new User();
        user.setId(42L);
        user.setUsername("u");
        user.setEmail("e");
        UserDto dto = userService.toDto(user);
        assertEquals(42L, dto.getId());
        assertEquals("u", dto.getUsername());
        assertEquals("e", dto.getEmail());
    }
}
