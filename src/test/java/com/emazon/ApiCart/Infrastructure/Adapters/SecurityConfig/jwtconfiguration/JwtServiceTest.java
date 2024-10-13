package com.emazon.ApiCart.Infrastructure.Adapters.SecurityConfig.jwtconfiguration;

import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtServiceTest {

    private JwtService jwtService;

    @Mock
    private UserDetails userDetails;
    String username;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtService = new JwtService();
        String secretKey = "mysecretkey12345678901234567890123456789012";
        ReflectionTestUtils.setField(jwtService, "secretKey", secretKey);
        username = "testUser";

    }

    @Test
    void extractUserId_shouldReturnUserIdFromToken() {
        // Arrange
        String token = generateTestToken(username);

        // Act
        String actualUserId = jwtService.extractUserId(token);

        // Assert
        assertEquals(username, actualUserId);
    }

    @Test
    void isTokenValid_shouldReturnTrue_whenTokenIsValid() {
        // Arrange
        when(userDetails.getUsername()).thenReturn(username);
        String token = generateTestToken(username);

        // Act
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void isTokenValid_shouldReturnFalse_whenTokenIsInvalid() {
        when(userDetails.getUsername()).thenReturn(username);

        String invalidToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0VXNlciIsImV4cCI6MTYzMTI0NTY5OX0.invalidSignature";

        boolean isValid = jwtService.isTokenValid(invalidToken, userDetails);

        assertFalse(isValid);
    }


    private String generateTestToken(String username) {
        Key key = ReflectionTestUtils.invokeMethod(jwtService, "getSignInKey");
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(key)
                .compact();
    }
}
