package com.bharat.EcomUserAuthService.controller;

import com.bharat.EcomUserAuthService.dto.RoleRequestDTO;
import com.bharat.EcomUserAuthService.dto.RoleResponseDTO;
import com.bharat.EcomUserAuthService.entity.Role;
import com.bharat.EcomUserAuthService.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @PostMapping("/create")
    public ResponseEntity<Role> createRole(@RequestBody RoleRequestDTO roleRequestDTO){
        Role role = roleService.createRole(roleRequestDTO);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

}
