package com.revature.cardfans.services;

import java.util.Optional;

import com.revature.cardfans.models.User;
import com.revature.cardfans.models.payload.AuthResponse;

public interface IUserService {

    public Optional<User> registerUser(User user);

    public Optional<AuthResponse> login(String userName, String password);

    public Optional<User> getUserById(int id);

    public Optional<User> updateUserInfo(User user);

}
