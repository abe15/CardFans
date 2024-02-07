package com.revature.cardfans.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.cardfans.models.Authority;
import com.revature.cardfans.models.Order;
import com.revature.cardfans.models.OrderItem;
import com.revature.cardfans.models.Product;
import com.revature.cardfans.models.User;
import com.revature.cardfans.models.payload.OrderEntry;
import com.revature.cardfans.models.payload.PlaceOrderRequest;
import com.revature.cardfans.models.payload.UserJwtInfo;
import com.revature.cardfans.services.OrderService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = ("/api/v1/orders"), produces = "application/json")
@CrossOrigin(origins = "*")
public class OrderController {

    private OrderService orderServ;

    @Autowired
    public OrderController(OrderService orderServ) {
        this.orderServ = orderServ;

    }

    /*
     * Creates an order
     */
    @PostMapping("")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody PlaceOrderRequest orderRequest, Authentication auth) {

        log.info("Attempting to create order");
        log.info(orderRequest.toString());
        UserJwtInfo userInfo = (UserJwtInfo) auth.getPrincipal();
        orderRequest.setUserId(userInfo.getUserId());
        Optional<Order> order = orderServ.saveOrder(orderRequest);
        return order.map(ResponseEntity::ok).orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

    }

}
