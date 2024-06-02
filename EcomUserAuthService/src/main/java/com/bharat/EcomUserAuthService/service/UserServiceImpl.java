package com.bharat.EcomUserAuthService.service;

import com.bharat.EcomUserAuthService.dto.LoginRequestDTO;
import com.bharat.EcomUserAuthService.dto.SignupRequestDTO;
import com.bharat.EcomUserAuthService.dto.UserResponseDTO;
import com.bharat.EcomUserAuthService.entity.Role;
import com.bharat.EcomUserAuthService.entity.User;
import com.bharat.EcomUserAuthService.exception.InvalidCredentialException;
import com.bharat.EcomUserAuthService.exception.RoleNotFoundException;
import com.bharat.EcomUserAuthService.exception.UserNotFoundException;
import com.bharat.EcomUserAuthService.repository.RoleRepository;
import com.bharat.EcomUserAuthService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserResponseDTO signup(SignupRequestDTO signupRequestDTO) throws RoleNotFoundException {
        Role role = roleRepository.findById(signupRequestDTO.getRoleId()).orElseThrow(
                () -> new RoleNotFoundException("Role not found")
        );
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setName(signupRequestDTO.getName());
        user.setEmailId(signupRequestDTO.getEmail());
        user.setRoles(List.of(role));
        user.setPassword(encoder.encode(signupRequestDTO.getPassword()));

        return UserResponseDTO.from(userRepository.save(user));
    }

    @Override
    public UserResponseDTO login(LoginRequestDTO loginRequestDTO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User savedUser = userRepository.findByEmailId(loginRequestDTO.getEmail()).orElseThrow(
                () -> new UserNotFoundException("No user found")
        );
        if(encoder.matches(loginRequestDTO.getPassword(),savedUser.getPassword())){
            String userdata = savedUser.getEmailId() + savedUser.getPassword() + LocalDateTime.now();
            String token = encoder.encode(userdata);
            savedUser.setToken(token);
        }
        else{
            throw new InvalidCredentialException();
        }
        savedUser = userRepository.save(savedUser);
        return UserResponseDTO.from(savedUser);
    }

    @Override
    public boolean validateToken(String token) {
        User saveduser = userRepository.findByToken(token).orElseThrow(
                () -> new InvalidCredentialException("token is not valid")
        );
        return true;
    }

    @Override
    public boolean logout(String token) {
        User saveduser = userRepository.findByToken(token).orElseThrow(
                () -> new InvalidCredentialException("token is not valid")
        );
        saveduser.setToken(null);
        userRepository.save(saveduser);
        return true;
    }
}


