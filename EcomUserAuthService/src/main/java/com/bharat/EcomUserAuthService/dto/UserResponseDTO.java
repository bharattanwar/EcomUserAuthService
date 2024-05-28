package com.bharat.EcomUserAuthService.dto;

import com.bharat.EcomUserAuthService.entity.Role;
import com.bharat.EcomUserAuthService.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserResponseDTO {
    private String name;
    private String email;
    private List<RoleResponseDTO> roles;

    public static UserResponseDTO from(User user) {
        if (user == null)
            return null;
            UserResponseDTO userResponseDTO = new UserResponseDTO();
            userResponseDTO.name = user.getName();
            userResponseDTO.email = user.getEmailId();
            userResponseDTO.roles = new ArrayList<>();
            for(Role role : user.getRoles()){
                RoleResponseDTO responseDTO = new RoleResponseDTO();
                responseDTO.setDesc(role.getDescription());
                responseDTO.setRole(role.getRole());
                userResponseDTO.roles.add(responseDTO);
            }
            return userResponseDTO;
    }

    public static User from(UserResponseDTO userResponseDTO){
        return null;
    }
}