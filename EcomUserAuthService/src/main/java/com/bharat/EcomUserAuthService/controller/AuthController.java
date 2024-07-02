package com.bharat.EcomUserAuthService.controller;

import com.bharat.EcomUserAuthService.dto.*;
import com.bharat.EcomUserAuthService.entity.SessionStatus;
import com.bharat.EcomUserAuthService.entity.User;
import com.bharat.EcomUserAuthService.exception.UserAlreadyExistsException;
import com.bharat.EcomUserAuthService.exception.UserDoesNotExistException;
import com.bharat.EcomUserAuthService.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.antlr.v4.runtime.misc.Pair;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto request) throws UserDoesNotExistException {
        return authService.login(request.getEmail(), request.getPassword());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto request) {
        return authService.logout(request.getToken(), request.getUserId());
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignupRequestDto request) throws UserAlreadyExistsException {
        UserDto userDto = authService.signUp(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/validate")
    public ResponseEntity<ValidateTokenResponseDto> validateToken(@RequestBody ValidateTokenRequestDto request) {
        Optional<UserDto> userDtoOptional = authService.validate(request.getToken(), request.getUserId());

        ValidateTokenResponseDto response = new ValidateTokenResponseDto();
        if (userDtoOptional.isEmpty()) {
            response.setSessionStatus(SessionStatus.INVALID);
        } else {
            response.setSessionStatus(SessionStatus.ACTIVE);
            response.setUserDto(userDtoOptional.get());
        }

        return ResponseEntity.ok(response);
    }
}