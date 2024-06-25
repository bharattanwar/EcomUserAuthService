package com.bharat.EcomUserAuthService.service.Implementations;

import com.bharat.EcomUserAuthService.dto.RoleRequestDTO;
import com.bharat.EcomUserAuthService.entity.Role;
import com.bharat.EcomUserAuthService.repository.RoleRepository;
import com.bharat.EcomUserAuthService.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role createRole(RoleRequestDTO roleRequestDTO) {
        Role role = new Role();
        role.setRoleName(roleRequestDTO.getRoleName());
        role.setDescription(roleRequestDTO.getDescription());
        return roleRepository.save(role);
    }
}
