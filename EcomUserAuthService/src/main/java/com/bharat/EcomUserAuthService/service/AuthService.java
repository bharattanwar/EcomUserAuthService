package com.bharat.EcomUserAuthService.service;

import org.antlr.v4.runtime.misc.Pair;
import org.springframework.util.MultiValueMap;
import com.bharat.EcomUserAuthService.entity.User;

public interface AuthService {
    User signUp(String email, String password);

    Pair<User, MultiValueMap<String, String>> login(String email, String password);

    Boolean validate(String token, Long userId);
}
