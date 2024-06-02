package com.bharat.EcomUserAuthService.dto;

import com.bharat.EcomUserAuthService.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequestDTO {
    private String roleName;
    private String description;
}
