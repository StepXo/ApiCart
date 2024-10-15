package com.emazon.ApiCart.Infrastructure.Persistance.Entity;

import com.emazon.ApiCart.Infrastructure.Utils.InfraConstants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = InfraConstants.TABLE_CART)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    private List<Long> item;

    @Column(unique = true)
    private long userId;

    private List<Long> quantity;

    private String actualizationDate;

    private String creationDate;
}
