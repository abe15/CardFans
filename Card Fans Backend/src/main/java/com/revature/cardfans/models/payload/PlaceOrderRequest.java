package com.revature.cardfans.models.payload;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.revature.cardfans.models.User;

import lombok.Data;

@Data
public class PlaceOrderRequest {

    private List<OrderEntry> items;
     private int userId ;
     private String firstName; 
     private String lastName ;
     private String email ;
     private String phoneNumber ;
     private String address1 ;
     private String address2;
     private String city ;
     private String state;
     private String zipCode ;
     private String country ;

     private double total;
    




    public PlaceOrderRequest() {
        items = new ArrayList<>();
    }

    public void insertOrderItem(OrderEntry o) {
        items.add(o);
    }
}
