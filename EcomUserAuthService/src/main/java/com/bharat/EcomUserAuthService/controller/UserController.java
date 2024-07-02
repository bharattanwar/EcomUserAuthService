package com.bharat.EcomUserAuthService.controller;

import com.bharat.EcomUserAuthService.dto.SetUserRolesRequestDTO;
import com.bharat.EcomUserAuthService.dto.UserDto;
import com.bharat.EcomUserAuthService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    public UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable Long id){
        System.out.println(id);
        UserDto userResponseDTO = userService.getUserDetails(id);
        return ResponseEntity.ok(userResponseDTO);
    }
    @PostMapping("/{id}/roles")
    public ResponseEntity<UserDto> setUserRoles(@PathVariable Long userId, @RequestBody SetUserRolesRequestDTO request) {
        UserDto userDto = userService.setUserRoles(userId, request.getRoleIds());
        return ResponseEntity.ok(userDto);
    }

}
