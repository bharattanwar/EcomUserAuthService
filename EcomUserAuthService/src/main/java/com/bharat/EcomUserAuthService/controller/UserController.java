package com.bharat.EcomUserAuthService.controller;

import com.bharat.EcomUserAuthService.dto.LoginRequestDTO;
import com.bharat.EcomUserAuthService.dto.SignupRequestDTO;
import com.bharat.EcomUserAuthService.dto.UserResponseDTO;
import com.bharat.EcomUserAuthService.exception.RoleNotFoundException;
import com.bharat.EcomUserAuthService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> Login(@RequestBody LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(userService.login(loginRequestDTO));
    }
    @GetMapping("/logout")
    public ResponseEntity<Boolean> Logout(@RequestHeader("Authorisation") String authToken){
        return ResponseEntity.ok(userService.logout(authToken));
    }
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> signup(@RequestBody SignupRequestDTO signupRequestDTO) throws RoleNotFoundException {
        return ResponseEntity.ok(userService.signup(signupRequestDTO));
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestHeader("Authorisation") String authToken){
        return ResponseEntity.ok(userService.validateToken(authToken));
    }
} 
