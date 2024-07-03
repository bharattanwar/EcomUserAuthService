package com.bharat.EcomUserAuthService.service;

import com.bharat.EcomUserAuthService.entity.Role;
import com.bharat.EcomUserAuthService.repository.RoleRepository;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role createRole(String name) {
        Role role = new Role();
//        role.setRole(name);

        return roleRepository.save(role);
    }
}
