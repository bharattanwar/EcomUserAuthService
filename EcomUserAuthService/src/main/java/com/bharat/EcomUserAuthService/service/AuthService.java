package com.bharat.EcomUserAuthService.service;

import com.bharat.EcomUserAuthService.dto.UserResponseDTO;
import com.bharat.EcomUserAuthService.entity.SessionStatus;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    UserResponseDTO signUp(String email, String password);

    UserResponseDTO login(String email, String password);

    SessionStatus validate(String token, Long userId);

    ResponseEntity<Void> logout(String token, Long userId);
}
