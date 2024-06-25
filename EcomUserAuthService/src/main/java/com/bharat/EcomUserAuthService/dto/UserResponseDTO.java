package com.bharat.EcomUserAuthService.dto;

import com.bharat.EcomUserAuthService.entity.Role;
import com.bharat.EcomUserAuthService.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class UserResponseDTO {
    private String email;
    private Set<Role> roles = new HashSet<>();

    public static UserResponseDTO from(User user) {
        UserResponseDTO userDto = new UserResponseDTO();
        userDto.setEmail(user.getEmailId());
//      userDto.setRoles(user.getRoles());
        return userDto;
    }
}