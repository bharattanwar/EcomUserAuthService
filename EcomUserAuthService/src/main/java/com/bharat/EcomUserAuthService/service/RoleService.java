package com.bharat.EcomUserAuthService.service;

import com.bharat.EcomUserAuthService.dto.RoleRequestDTO;
import com.bharat.EcomUserAuthService.dto.RoleResponseDTO;

public interface RoleService {
    RoleResponseDTO createRole(RoleRequestDTO roleRequestDTO);

}
