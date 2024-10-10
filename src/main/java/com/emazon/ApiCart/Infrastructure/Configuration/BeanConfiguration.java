package com.emazon.ApiCart.Infrastructure.Configuration;

import com.emazon.ApiCart.Application.Handler.CartHandler;
import com.emazon.ApiCart.Application.Service.CartService;
import com.emazon.ApiCart.Domain.Api.CartServicePort;
import com.emazon.ApiCart.Domain.Spi.CartPersistencePort;
import com.emazon.ApiCart.Domain.Spi.UserJwtPort;
import com.emazon.ApiCart.Domain.Usecase.CartUseCase;
import com.emazon.ApiCart.Infrastructure.Adapters.Jpa.CartJpa;
import com.emazon.ApiCart.Infrastructure.Adapters.SecurityConfig.jwtconfiguration.JwtService;
import com.emazon.ApiCart.Infrastructure.Persistance.Mapper.CartMapper;
import com.emazon.ApiCart.Infrastructure.Persistance.Repository.CartRepository;
import com.emazon.ApiCart.Infrastructure.Utils.UserExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final CartMapper mapper;
    private final CartRepository cartRepository;
    private final CartHandler cartHandler;
    private final JwtService jwtService;
    @Bean
    public CartPersistencePort cartRepositoryPort(){
        return new CartJpa(mapper,cartRepository);
    }

    @Bean
    public UserJwtPort userJwtPort() {
        return new UserExtractor(jwtService);
    }

    @Bean
    public CartServicePort cartServicePort(){
        return new CartUseCase(cartRepositoryPort(),userJwtPort());
    }

    @Bean
    public CartService cartService() {
        return new CartService(cartServicePort(),cartHandler);
    }

}
