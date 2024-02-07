package com.revature.cardfans.models.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class OrderEntry {

    private int productId;
   
    private int quantity;

}
