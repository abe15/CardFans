package com.revature.cardfans.dao;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.revature.cardfans.models.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String userName);

    Optional<User> findByUserId(int userId);

    User save(User user);

}