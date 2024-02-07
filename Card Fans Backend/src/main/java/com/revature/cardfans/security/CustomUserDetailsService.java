package com.revature.cardfans.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.revature.cardfans.dao.UserDao;
import com.revature.cardfans.models.User;

import java.util.function.Supplier;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) {
        Supplier<UsernameNotFoundException> s = () -> new UsernameNotFoundException("Authentication Error");

        User u = userRepository.findByUsername(username).orElseThrow(s);

        return new CustomUserDetails(u);
    }
}
