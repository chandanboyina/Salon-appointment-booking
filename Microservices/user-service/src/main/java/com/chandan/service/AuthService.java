package com.chandan.service;

import com.chandan.payload.response.AuthResponse;
import com.chandan.payload.dto.SignupDTO;

public interface AuthService {
    AuthResponse login(String username, String password) throws Exception;
    AuthResponse signup(SignupDTO req) throws Exception;
    AuthResponse getAccessTokenFromRefreshToken(String refreshToken) throws Exception;
}
