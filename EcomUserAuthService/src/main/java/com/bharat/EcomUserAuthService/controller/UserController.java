package com.bharat.EcomUserAuthService.controller;

import com.bharat.EcomUserAuthService.dto.LoginRequestDTO;
import com.bharat.EcomUserAuthService.dto.SignupRequestDTO;
import com.bharat.EcomUserAuthService.dto.UserResponseDTO;
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
        return null;
    }
    @GetMapping("/logout")
    public ResponseEntity<UserResponseDTO> Logout(){
        return null;
    }
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> Signup(@RequestBody SignupRequestDTO signupRequestDTO) {
        return null;
    }

    @GetMapping("/validate")
    public ResponseEntity validate(){
        return null;
    }
}
