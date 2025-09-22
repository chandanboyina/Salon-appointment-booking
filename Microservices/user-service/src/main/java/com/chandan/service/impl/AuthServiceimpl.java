package com.chandan.service.impl;

import com.chandan.model.User;
import com.chandan.payload.response.AuthResponse;
import com.chandan.payload.dto.SignupDTO;
import com.chandan.payload.response.TokenResponse;
import com.chandan.repository.UserRepository;
import com.chandan.service.AuthService;
import com.chandan.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceimpl implements AuthService {

    private final UserRepository userRepository;
    private final KeycloakService keycloakService;

    @Override
    public AuthResponse login(String email, String password) throws Exception {

        TokenResponse tokenResponse=keycloakService.getAdminAccessToken(email,
                password,"password", null);

        AuthResponse authResponse=new AuthResponse();
        authResponse.setRefresh_token(tokenResponse.getRefreshToken());
        authResponse.setJwt(tokenResponse.getAccessToken());
        authResponse.setMessage("Login Successful");
        return authResponse;
    }

    @Override
    public AuthResponse signup(SignupDTO req) throws Exception {
        keycloakService.createUser(req);
        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(req.getPassword());
        user.setEmail(req.getEmail());
        user.setRole(req.getRole());
        user.setFullName(req.getFullName());
        user.setLastName(req.getLastName());
        user.setFirstName(req.getFirstName());
        user.setCreatedAt(LocalDate.from(LocalDateTime.now()));

        userRepository.save(user);

        TokenResponse tokenResponse=keycloakService.getAdminAccessToken(req.getUsername(),
                req.getPassword(),"password", null);

        AuthResponse authResponse=new AuthResponse();
        authResponse.setRefresh_token(tokenResponse.getRefreshToken());
        authResponse.setJwt(tokenResponse.getAccessToken());
        authResponse.setRole(user.getRole());
        authResponse.setMessage("Registration Successful");

        return authResponse;
    }

    @Override
    public AuthResponse getAccessTokenFromRefreshToken(String refreshToken) throws Exception {

        TokenResponse tokenResponse=keycloakService.getAdminAccessToken(null,null,"refresh_token", refreshToken);

        AuthResponse authResponse=new AuthResponse();
        authResponse.setRefresh_token(tokenResponse.getRefreshToken());
        authResponse.setJwt(tokenResponse.getAccessToken());
        authResponse.setMessage("Login Successful");
        return authResponse;
    }
}
