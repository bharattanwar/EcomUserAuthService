package com.bharat.EcomUserAuthService.dto;

import com.bharat.EcomUserAuthService.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleResponseDTO {
    private String role;
    private String desc;

    public static RoleResponseDTO from(Role role) {
        RoleResponseDTO responseDTO = new RoleResponseDTO();
        responseDTO.role = role.getRoleName();
        responseDTO.desc = role.getDescription();
        return responseDTO;
    }
}
