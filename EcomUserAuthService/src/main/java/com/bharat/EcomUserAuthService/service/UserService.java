package com.bharat.EcomUserAuthService.service;

import com.bharat.EcomUserAuthService.dto.LoginRequestDTO;
import com.bharat.EcomUserAuthService.dto.SignupRequestDTO;
import com.bharat.EcomUserAuthService.dto.UserResponseDTO;
import com.bharat.EcomUserAuthService.exception.RoleNotFoundException;

public interface UserService {
    UserResponseDTO signup(SignupRequestDTO signupRequestDTO) throws RoleNotFoundException;
    UserResponseDTO login(LoginRequestDTO loginRequestDTO);
    boolean validateToken(String token);
    boolean logout(String token);
}
