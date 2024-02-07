package com.revature.cardfans.models.payload;

import com.revature.cardfans.models.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AuthResponse {
    public User user;
    public String accessToken;
    // getters and setters are not shown...
}
