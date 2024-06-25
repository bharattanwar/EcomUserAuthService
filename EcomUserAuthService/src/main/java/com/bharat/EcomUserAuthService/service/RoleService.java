package com.bharat.EcomUserAuthService.service;

import com.bharat.EcomUserAuthService.dto.RoleRequestDTO;
import com.bharat.EcomUserAuthService.entity.Role;

public interface RoleService {
    Role createRole(RoleRequestDTO roleRequestDTO);
}
