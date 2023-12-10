package com.apapedia.user.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seller")
@SQLDelete(sql = "UPDATE seller SET is_deleted = true WHERE id=?")
// @Where(clause = "is_deleted=false")
public class Seller extends UserModel {
   
    // ke category
    // string
    @NotNull
    @Column(name = "category", nullable = false)
    private Long category;
}

