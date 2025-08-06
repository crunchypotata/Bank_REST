package com.example.bankcards.service;

import com.example.bankcards.dto.*;
import com.example.bankcards.entity.User;
import com.example.bankcards.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    private UserService userService;
    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        jwtService = mock(JwtService.class);
        passwordEncoder = mock(PasswordEncoder.class);
        authenticationManager = mock(AuthenticationManager.class);

        authenticationService = new AuthenticationService(
                userService, jwtService, passwordEncoder, authenticationManager
        );
    }

    @Test
    void signUp_ShouldReturnJwtResponse() {
        // Arrange
        SignUpRequest request = new SignUpRequest("alex12", "alex@mail.com", "pass123");
        User user = new User();
        user.setUsername("alex12");
        when(userService.create(any(CreateUserDto.class))).thenReturn(any());
        when(userService.findEntityByUsername("alex12")).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("mocked-jwt-token");

        // Act
        JwtAuthenticationResponse response = authenticationService.signUp(request);

        // Assert
        assertNotNull(response);
        assertEquals("mocked-jwt-token", response.getToken());
        verify(userService).create(any(CreateUserDto.class));
        verify(jwtService).generateToken(user);
    }

    @Test
    void signIn_ShouldReturnJwtResponse() {
        // Arrange
        SignInRequest request = new SignInRequest("alex12", "pass123");
        User user = new User();
        user.setUsername("alex12");
        when(userService.findEntityByUsername("alex12")).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("mocked-jwt-token");

        // Act
        JwtAuthenticationResponse response = authenticationService.signIn(request);

        // Assert
        assertNotNull(response);
        assertEquals("mocked-jwt-token", response.getToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateToken(user);
    }
}
