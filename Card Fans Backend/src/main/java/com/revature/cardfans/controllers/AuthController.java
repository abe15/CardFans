package com.revature.cardfans.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.cardfans.models.User;
import com.revature.cardfans.models.payload.AuthResponse;
import com.revature.cardfans.models.payload.LoginRequest;

import com.revature.cardfans.services.IUserService;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
@RestController
@RequestMapping(path = ("/api/v1/auth"), produces = "application/json")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private IUserService uServ;

    /* Handles sing in/login attempts */
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Optional<AuthResponse> response = uServ.login(loginRequest.getUsername(), loginRequest.getPassword());
        return response.map(ResponseEntity::ok).orElse(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));

    }

    /* Handles user registration */

    @PostMapping("/signup")
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {

        Optional<User> _user = uServ.registerUser(user);
        return _user.map(ResponseEntity::ok).orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

    }

}
