package com.emazon.ApiCart.Infrastructure.Persistance.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    private long itemId;

    @Column(unique = true)
    private long userId;

    private int quantity;

    private String actualizationDate;

    private String creationDate;
}
