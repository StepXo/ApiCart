package com.emazon.ApiCart.Infrastructure.Utils;

import com.emazon.ApiCart.Infrastructure.Adapters.SecurityConfig.jwtconfiguration.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserExtractorTest {

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserExtractor userExtractor;

    private MockHttpServletRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
    }

    @Test
    void extractUserId_shouldReturnUserId_whenTokenIsPresent() {
        String token = "validToken";
        String userId = "12345";
        request.addHeader("Authorization", "Bearer " + token);
        when(jwtService.extractUserId(token)).thenReturn(userId);

        String extractedUserId = userExtractor.extractUserId();

        assertNotNull(extractedUserId);
        assertEquals(userId, extractedUserId);
    }

    @Test
    void extractUserId_shouldReturnNull_whenNoTokenIsPresent() {

        String extractedUserId = userExtractor.extractUserId();

        assertNull(extractedUserId);
    }

    @Test
    void extractUserId_shouldReturnNull_whenAuthorizationHeaderIsNotBearerToken() {
        request.addHeader("Authorization", "Basic notABearerToken");

        String extractedUserId = userExtractor.extractUserId();

        assertNull(extractedUserId);
    }

    @Test
    void extractUserId_shouldReturnNull_whenTokenIsInvalid() {
        String invalidToken = "invalidToken";
        request.addHeader("Authorization", "Bearer " + invalidToken);
        when(jwtService.extractUserId(invalidToken)).thenReturn(null);

        String extractedUserId = userExtractor.extractUserId();

        assertNull(extractedUserId);
    }
}
