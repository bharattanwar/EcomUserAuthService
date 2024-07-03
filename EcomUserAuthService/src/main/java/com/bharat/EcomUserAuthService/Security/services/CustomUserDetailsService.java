package com.bharat.EcomUserAuthService.Security.services;

import com.bharat.EcomUserAuthService.Security.models.CustomUserDetails;
import com.bharat.EcomUserAuthService.entity.User;
import com.bharat.EcomUserAuthService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException("user Not Found");
        }
        User user=optionalUser.get();
        return new CustomUserDetails(user);
    }
}