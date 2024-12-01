package com.cusca.auth.services;

import com.cusca.auth.config.CustomException;
import com.cusca.auth.dto.Request.UserRequestDto;
import com.cusca.auth.models.UserModel;
import com.cusca.auth.util.JwtUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AuthService {
    private final UserService userService;

    private final JwtUtil jwtUtil;

    public AuthService(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    public String authenticate(UserRequestDto userRequest) {
        UserModel user = userService.findByUsernameAndPassword(userRequest.getUsername(), userRequest.getPassword());
        if (user == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid credentials");
        }

        return jwtUtil.generateToken(user);
    }
}
