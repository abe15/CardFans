package com.revature.cardfans.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.revature.cardfans.dao.UserDao;
import com.revature.cardfans.models.User;
import com.revature.cardfans.models.payload.AuthResponse;
import com.revature.cardfans.security.CustomUserDetails;
import com.revature.cardfans.security.JwtTokenUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j // creates log object usage: log.info("test")
@Service

public class UserServiceImpl implements IUserService {

    private UserDao userDao;
    @Autowired
    AuthenticationManager authManager;

    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    JwtTokenUtil jwtUtil;

    @Autowired
    public UserServiceImpl(UserDao u, PasswordEncoder p) {
        userDao = u;
        bCryptPasswordEncoder = p;

    }

    @Override
    public Optional<User> registerUser(User user) {
        log.info("Attemping to register user");
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return Optional.of(userDao.save(user));

    }

    @Override
    public Optional<User> getUserById(int id) {
        log.info("Searching for user with id {}", id);
        return userDao.findByUserId(id);
    }

    // If user updated successful
    // return updated user info
    // else returns
    @Override
    public Optional<User> updateUserInfo(User user) {

        log.info("Attemping to update user info");
        Optional<User> user2 = Optional.of(userDao.save(user));

        user2.ifPresentOrElse((x) -> log.info("User info updated succesfully"), () -> log.info("Unsucceful update"));

        return user2;
    }

    // Lookup username info, if valid user with username
    // compares password and returns user encased in optional
    // else returns empty optional
    @Override
    public Optional<AuthResponse> login(String userName, String password) {

        try {
            Authentication authentication = authManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(userName, password));

            User user = ((CustomUserDetails) (authentication.getPrincipal())).getUser();
            String accessToken = jwtUtil.generateAccessToken(user);
            AuthResponse response = new AuthResponse(user, accessToken);
            return Optional.of(response);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return Optional.empty();

    }

}