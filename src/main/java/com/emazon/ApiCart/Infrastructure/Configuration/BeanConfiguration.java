package com.emazon.ApiCart.Infrastructure.Configuration;

import com.emazon.ApiCart.Application.Handler.CartHandler;
import com.emazon.ApiCart.Application.Handler.ItemHandler;
import com.emazon.ApiCart.Application.Service.CartService;
import com.emazon.ApiCart.Application.Utils.FilterUtil;
import com.emazon.ApiCart.Application.Utils.PaginationUtil;
import com.emazon.ApiCart.Application.Utils.SorterUtil;
import com.emazon.ApiCart.Domain.Api.CartServicePort;
import com.emazon.ApiCart.Domain.Api.ItemServicePort;
import com.emazon.ApiCart.Domain.Spi.CartPersistencePort;
import com.emazon.ApiCart.Domain.Spi.StockFeignPort;
import com.emazon.ApiCart.Domain.Spi.UserJwtPort;
import com.emazon.ApiCart.Domain.Usecase.CartUseCase;
import com.emazon.ApiCart.Domain.Usecase.ItemUseCase;
import com.emazon.ApiCart.Infrastructure.Adapters.Feign.IStockFeign;
import com.emazon.ApiCart.Infrastructure.Adapters.Feign.StockFeign;
import com.emazon.ApiCart.Infrastructure.Adapters.Jpa.CartJpa;
import com.emazon.ApiCart.Infrastructure.Adapters.SecurityConfig.jwtconfiguration.JwtService;
import com.emazon.ApiCart.Infrastructure.Persistance.Mapper.CartMapper;
import com.emazon.ApiCart.Infrastructure.Persistance.Mapper.ItemMapper;
import com.emazon.ApiCart.Infrastructure.Persistance.Repository.CartRepository;
import com.emazon.ApiCart.Infrastructure.Utils.UserExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final CartMapper mapper;
    private final ItemMapper itemMapper;
    private final CartRepository cartRepository;
    private final CartHandler cartHandler;
    private final ItemHandler itemHandler;
    private final SorterUtil sorterUtil;
    private final FilterUtil filterUtil;
    private final PaginationUtil paginationUtil;

    private final JwtService jwtService;
    private final IStockFeign stockFeign;
    @Bean
    public CartPersistencePort cartRepositoryPort(){
        return new CartJpa(mapper,cartRepository);
    }

    @Bean
    public UserJwtPort userJwtPort() {
        return new UserExtractor(jwtService);
    }
    @Bean
    public StockFeignPort stockFeignPort() {
        return new StockFeign(stockFeign,itemMapper);
    }

    @Bean
    public CartServicePort cartServicePort(){
        return new CartUseCase(cartRepositoryPort(),userJwtPort(),stockFeignPort());
    }
    @Bean
    public ItemServicePort itemServicePort(){
        return new ItemUseCase(stockFeignPort());
    }

    @Bean
    public CartService cartService() {
        return new CartService(cartServicePort(),cartHandler,itemServicePort(),itemHandler,sorterUtil,filterUtil,paginationUtil);
    }

}
