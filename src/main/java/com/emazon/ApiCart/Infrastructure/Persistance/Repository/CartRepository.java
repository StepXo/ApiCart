package com.emazon.ApiCart.Infrastructure.Persistance.Repository;

import com.emazon.ApiCart.Infrastructure.Persistance.Entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity,Long> {
    //Optional<CartEntity> findByItemId(long id);
    Optional<CartEntity> findByUserId(long id);

}
