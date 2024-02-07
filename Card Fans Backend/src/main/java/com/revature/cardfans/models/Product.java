package com.revature.cardfans.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.*;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "products")
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    @Column(name = "product_name")

   // @NotNull
    private String productName;

    // @Size(min = 2, max = 15)
    @Column(name = "suit_colors")
    // @NotNull
    private String suitColors;

    // @Size(min = 2, max = 15)
    // @NotNull
    @Column(name = "face_type")
    private String faceType;

    // @Size(min = 2, max = 20)
    // @Email
    @Column(name = "theme")
    // @NotNull
    private String theme;

    // @Size(min = 5)
    @Column(name = "price")
    //@NotNull
    private Double price;

    // @Size(min = 2, max = 20)
    @Column(name = "description")
    private String description;

    
    @Column(name = "quantity")
   
    private Integer quantity;

    @Column(name = "background")
    private String background;

    @Column(name = "imgUrl")
    private String imgUrl;
}
