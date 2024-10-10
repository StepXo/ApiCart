package com.emazon.ApiCart.Infrastructure.Configuration;

import com.emazon.ApiCart.Infrastructure.Utils.InfraConstants;
import com.emazon.ApiCart.Infrastructure.Utils.UserExtractor;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class FeignConfiguration {
    private final UserExtractor userExtractor;
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
}
