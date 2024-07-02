package com.bharat.EcomUserAuthService.service;

import com.bharat.EcomUserAuthService.dto.UserDto;
import com.bharat.EcomUserAuthService.entity.Role;
import com.bharat.EcomUserAuthService.entity.Session;
import com.bharat.EcomUserAuthService.entity.SessionStatus;
import com.bharat.EcomUserAuthService.entity.User;
import com.bharat.EcomUserAuthService.exception.UserAlreadyExistsException;
import com.bharat.EcomUserAuthService.exception.UserDoesNotExistException;
import com.bharat.EcomUserAuthService.repository.SessionRepository;
import com.bharat.EcomUserAuthService.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.*;

@Service
public class AuthService {
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private SessionRepository sessionRepository;
//    private BCryptPasswordEncoder bCryptPasswordEncodersswordEncoder;

    public AuthService(UserRepository userRepository, SessionRepository sessionRepository , PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<UserDto> login(String email, String password) throws UserDoesNotExistException {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new UserDoesNotExistException("User with email: " + email + " doesn't exist.");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

//        RandomStringUtils randomStringUtils = new RandomStringUtils();
        // TODO: Update here to use Jwt
        // Payload:
        // {
        //    userId:
        //    email:
        //    roles: [
        //    ]
        // }
        // Map<String, Object> claimsMap;
        // claimsMap.add(userId, 123);


        String token = RandomStringUtils.randomAscii(20);
        MultiValueMapAdapter<String, String > headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add("AUTH_TOKEN", token);

        Session session = new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setToken(token);
        session.setUser(user);
        sessionRepository.save(session);


        UserDto userDto = UserDto.from(user);
        ResponseEntity<UserDto> response = new ResponseEntity<>(
                userDto,
                headers,
                HttpStatus.OK
        );

        return response;
    }

    public ResponseEntity<Void> logout(String token, Long userId) {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

        if (sessionOptional.isEmpty()) {
            return null;
        }

        Session session = sessionOptional.get();

        session.setSessionStatus(SessionStatus.LOGGED_OUT);

        sessionRepository.save(session);

        return ResponseEntity.ok().build();
    }

    public UserDto signUp(String email, String password) throws UserAlreadyExistsException {

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (!userOptional.isEmpty()) {
            throw new UserAlreadyExistsException("User with " + email + " already exists.");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        User savedUser = userRepository.save(user);

        return UserDto.from(savedUser);
    }

    public Optional<UserDto> validate(String token, Long userId) {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

        if (sessionOptional.isEmpty()) {
            return Optional.empty();
        }

        Session session = sessionOptional.get();

        if (!session.getSessionStatus().equals(SessionStatus.ACTIVE)) {
            return Optional.empty();
        }

        User user = userRepository.findById(userId).get();

        UserDto userDto = UserDto.from(user);

//        if (!session.getExpiringAt() > new Date()) {
//            return SessionStatus.EXPIRED;
//        }

        return Optional.of(userDto);
    }

}