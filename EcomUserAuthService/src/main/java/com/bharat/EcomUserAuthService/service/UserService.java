package com.bharat.EcomUserAuthService.service;

import com.bharat.EcomUserAuthService.dto.LoginRequestDTO;
import com.bharat.EcomUserAuthService.dto.SignupRequestDTO;
import com.bharat.EcomUserAuthService.dto.UserResponseDTO;
import com.bharat.EcomUserAuthService.entity.User;
import com.bharat.EcomUserAuthService.exception.RoleNotFoundException;

import java.util.List;

public interface UserService {

    UserResponseDTO getUserDetails(Long id);
    UserResponseDTO setUserRoles(Long userId, List<Long> roleIds);
}
