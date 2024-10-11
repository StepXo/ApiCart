package com.emazon.ApiCart.Infrastructure.Persistance.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<Double> price;

    private String actualizationDate;

    private String creationDate;
}
