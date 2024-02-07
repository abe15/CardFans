package com.revature.cardfans.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.revature.cardfans.models.payload.UserJwtInfo;

import io.jsonwebtoken.Claims;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    CustomUserDetailsService j;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Get token from request
        final String tokenFromRequest = request.getHeader("Authorization");

        Claims userClaim = null;
        String jwtToken = null;

        // Parse token, and validate if present
        if (tokenFromRequest != null && tokenFromRequest.startsWith("Bearer ")) {
            jwtToken = tokenFromRequest.substring(7);
            try {
                userClaim = jwtTokenUtil.validateToken(jwtToken);
            } catch (IllegalArgumentException e) {

            }
        }

        /// userClaim is null when token invalid
        // execute if valid claim
        if (userClaim != null) {

            // get userid and username from token
            UserJwtInfo j = new UserJwtInfo(userClaim.getSubject(),
                    Integer.parseInt(userClaim.get("userId").toString()));

            // construct token
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    j, null, null);

            // Set authenticated to TRUE
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        // do other filters
        filterChain.doFilter(request, response);
    }

}