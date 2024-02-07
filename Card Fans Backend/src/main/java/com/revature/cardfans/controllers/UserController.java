package com.revature.cardfans.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.cardfans.models.Order;
import com.revature.cardfans.models.User;
import com.revature.cardfans.models.payload.UserJwtInfo;
import com.revature.cardfans.services.IUserService;
import com.revature.cardfans.services.OrderService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

@Slf4j
@RestController
@RequestMapping(path = ("/api/v1/users"), produces = "application/json")
@CrossOrigin(origins = "*")
public class UserController {

    private IUserService uServ;
    private OrderService orderServ;

    @Autowired
    public UserController(IUserService uServ, OrderService orderServ) {
        this.uServ = uServ;
        this.orderServ = orderServ;

    }

    /*
     * Check if user with id exists
     * If user exists, returns user info and response code CREATED
     * else returns response code NOT_FOUND
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Integer id, Authentication auth) {
        UserJwtInfo userInfo = (UserJwtInfo) auth.getPrincipal();
        Optional<User> user = uServ.getUserById(userInfo.getUserId());
        return user.map(ResponseEntity::ok).orElse(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));

    }

    /* Handles user info updates request */
    @PatchMapping("")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {

        Optional<User> _user = uServ.updateUserInfo(user);
        return _user.map(ResponseEntity::ok).orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

    }

    /*
     * Gets all orders placed by user_id
     * Checks if user is getting own orders
     */
    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<Order>> getOrdersSecure(@PathVariable("userId") Integer userId, Authentication auth) {

        UserJwtInfo userInfo = (UserJwtInfo) auth.getPrincipal();
        if (userInfo.getUserId() != userId) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<Order> orders = orderServ.getOrdersByUserId(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);

    }

}