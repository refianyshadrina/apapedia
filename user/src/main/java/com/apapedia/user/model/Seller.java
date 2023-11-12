package com.apapedia.user.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seller")
public class Seller extends UserModel {
   
    // ke category
    @NotNull
    @Column(name = "category", nullable = false)
    private Long category;
}

