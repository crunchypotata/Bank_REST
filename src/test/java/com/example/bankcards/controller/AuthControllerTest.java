package com.example.bankcards.controller;

import com.example.bankcards.dto.JwtAuthenticationResponse;
import com.example.bankcards.dto.SignInRequest;
import com.example.bankcards.dto.SignUpRequest;
import com.example.bankcards.security.JwtService;
import com.example.bankcards.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private com.example.bankcards.service.UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void login_ReturnsJwtResponse_WhenCredentialsAreValid() throws Exception {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setUsername("alex12");
        signInRequest.setPassword("password123");

        JwtAuthenticationResponse jwtResponse = new JwtAuthenticationResponse("mocked-jwt-token");

        Mockito.when(authenticationService.signIn(Mockito.any(SignInRequest.class)))
                .thenReturn(jwtResponse);

        mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mocked-jwt-token"));

    }

    @Test
    void signup_ReturnsJwtResponse_WhenCredentialsAreValid() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("validUser");
        signUpRequest.setEmail("valid@email.com");
        signUpRequest.setPassword("valid_password");

        JwtAuthenticationResponse jwtResponse = new JwtAuthenticationResponse("mocked-signup-jwt-token");

        Mockito.when(authenticationService.signUp(Mockito.any(SignUpRequest.class)))
                .thenReturn(jwtResponse);

        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mocked-signup-jwt-token"));
    }
}
