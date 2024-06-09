package com.bharat.EcomUserAuthService.controller;

import com.bharat.EcomUserAuthService.dto.UserResponseDTO;
import com.bharat.EcomUserAuthService.entity.User;
import com.bharat.EcomUserAuthService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    public UserService userService;

    @GetMapping("/{id}")
    public UserResponseDTO getUserDetails(@PathVariable Long id){
        System.out.println(id);
        User user=userService.getUserDetails(id);
        if(user == null)
            return null;
        return getUserDTO(user);
    }

    private UserResponseDTO getUserDTO(User user){
        UserResponseDTO userDTO = new UserResponseDTO();
        userDTO.setEmail(user.getEmailId());
        return userDTO;
    }
} 
