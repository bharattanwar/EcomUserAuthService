package com.bharat.EcomUserAuthService.service;

import com.bharat.EcomUserAuthService.entity.User;
import com.bharat.EcomUserAuthService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserDetails(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) return optionalUser.get();
        System.out.println("Control in UserService");
        return null;
    }

}