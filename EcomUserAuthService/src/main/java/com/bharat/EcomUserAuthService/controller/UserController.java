package com.bharat.EcomUserAuthService.controller;

import com.bharat.EcomUserAuthService.dto.UserDTO;
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
    public UserDTO getUserDetails(@PathVariable Long id){
        System.out.println(id);
        User user=userService.getUserDetails(id);
        if(user == null) return null;

        return getUserDTO(user);
    }

    private UserDTO getUserDTO(User user){
        UserDTO userDTO=new UserDTO();
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

}