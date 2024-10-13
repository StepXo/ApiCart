package com.emazon.ApiCart.Infrastructure.Configuration;

import com.emazon.ApiCart.Infrastructure.ExceptionHandler.ControllerErrorDecoder;
import com.emazon.ApiCart.Infrastructure.Utils.InfraConstants;
import com.emazon.ApiCart.Infrastructure.Utils.UserExtractor;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.ErrorDecoder;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class FeignConfiguration {
    private final UserExtractor userExtractor;
    private final ObjectMapper objectMapper;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                String token = InfraConstants.BEARER + userExtractor.getTokenFromRequest();
                template.header(InfraConstants.AUTHORIZATION, token);
            }
        };
    }
    @Bean
    public ErrorDecoder errorDecoder() {
        return new ControllerErrorDecoder(objectMapper);
    }
}
