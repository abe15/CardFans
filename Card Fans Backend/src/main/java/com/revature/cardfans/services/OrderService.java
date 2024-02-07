package com.revature.cardfans.services;

import java.util.List;
import java.util.Optional;

import com.revature.cardfans.models.Order;
import com.revature.cardfans.models.User;
import com.revature.cardfans.models.payload.PlaceOrderRequest;

public interface OrderService {

    public List<Order> getOrdersByUserId(Integer userId);

    // public List<Order> getOrdersByUsername(String userId);

    public Optional<Order> saveOrder(PlaceOrderRequest orderReq);

}
