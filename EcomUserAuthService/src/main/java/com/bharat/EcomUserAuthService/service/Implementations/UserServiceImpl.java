package com.bharat.EcomUserAuthService.service.Implementations;

import com.bharat.EcomUserAuthService.dto.UserResponseDTO;
import com.bharat.EcomUserAuthService.entity.Role;
import com.bharat.EcomUserAuthService.entity.User;
import com.bharat.EcomUserAuthService.repository.RoleRepository;
import com.bharat.EcomUserAuthService.repository.UserRepository;
import com.bharat.EcomUserAuthService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserResponseDTO getUserDetails(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return null;
        }
        return UserResponseDTO.from(optionalUser.get());
    }

    public UserResponseDTO setUserRoles(Long userId, List<Long> roleIds) {
        Optional<User> userOptional = userRepository.findById(userId);
        List<Role> roles = roleRepository.findAllByIdIn(roleIds);
        if (userOptional.isEmpty()) {
            return null;
        }
        User user = userOptional.get();
//        user.setRoles(Set.copyOf(roles));
        User savedUser = userRepository.save(user);
        return UserResponseDTO.from(savedUser);
    }
}