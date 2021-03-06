package com.melhamra.eatitbackend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melhamra.eatitbackend.configuration.SpringApplicationContext;
import com.melhamra.eatitbackend.dtos.UserDto;
import com.melhamra.eatitbackend.exceptions.EatItException;
import com.melhamra.eatitbackend.requests.UserLoginRequest;
import com.melhamra.eatitbackend.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            UserLoginRequest userLoginRequest = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequest.class);
            return this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword())
            );
        } catch (IOException e) {
            throw new EatItException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        User springUser = (User) authResult.getPrincipal();

        UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
        UserDto user = userService.getUserByEmail(springUser.getUsername());

        String jwt = Jwts.builder()
                .setSubject(user.getEmail())
                .claim("id", user.getPublicId())
                .claim("name", user.getFullName())
                .claim("roles", springUser.getAuthorities())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET)
                .compact();

        response.addHeader(SecurityConstants.HEADER_STRING, jwt);
        response.addHeader("id", user.getPublicId());
        response.getWriter()
                .write("{\"token\": \"" + jwt + "\", \"id\": \"" + user.getPublicId() + "\"}");
    }
}
