package com.bharat.EcomUserAuthService.controller;

import com.bharat.EcomUserAuthService.dto.SetUserRolesRequestDTO;
import com.bharat.EcomUserAuthService.dto.UserResponseDTO;
import com.bharat.EcomUserAuthService.entity.User;
import com.bharat.EcomUserAuthService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    public UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserDetails(@PathVariable Long id){
        System.out.println(id);
        UserResponseDTO userResponseDTO = userService.getUserDetails(id);
        return ResponseEntity.ok(userResponseDTO);
    }
    @PostMapping("/{id}/roles")
    public ResponseEntity<UserResponseDTO> setUserRoles(@PathVariable Long userId, @RequestBody SetUserRolesRequestDTO request) {
        UserResponseDTO userDto = userService.setUserRoles(userId, request.getRoleIds());
        return ResponseEntity.ok(userDto);
    }

}
