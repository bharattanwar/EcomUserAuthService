package com.bharat.EcomUserAuthService.service;

import com.bharat.EcomUserAuthService.dto.RoleRequestDTO;
import com.bharat.EcomUserAuthService.dto.RoleResponseDTO;
import com.bharat.EcomUserAuthService.entity.Role;
import com.bharat.EcomUserAuthService.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements  RoleService{

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleResponseDTO createRole(RoleRequestDTO roleRequestDTO) {
        Role role = new Role();
        role.setRoleName(roleRequestDTO.getRoleName());
        role.setDescription(roleRequestDTO.getDescription());
        return RoleResponseDTO.from(roleRepository.save(role));
    }
}
