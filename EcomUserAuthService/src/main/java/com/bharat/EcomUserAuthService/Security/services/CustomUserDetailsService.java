package com.bharat.EcomUserAuthService.Security.services;

import com.bharat.EcomUserAuthService.Security.models.CustomUserDetails;
import com.bharat.EcomUserAuthService.entity.User;
import com.bharat.EcomUserAuthService.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username + " doesn't exist.");
        }


        return new CustomUserDetails(user.get());
    }
}
