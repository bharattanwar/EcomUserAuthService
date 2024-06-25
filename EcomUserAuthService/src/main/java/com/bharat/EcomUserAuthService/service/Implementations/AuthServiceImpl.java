package com.bharat.EcomUserAuthService.service.Implementations;

import com.bharat.EcomUserAuthService.Client.KafkaProducerClient;
import com.bharat.EcomUserAuthService.dto.SendMessageDTO;
import com.bharat.EcomUserAuthService.dto.UserResponseDTO;
import com.bharat.EcomUserAuthService.entity.Role;
import com.bharat.EcomUserAuthService.entity.Session;
import com.bharat.EcomUserAuthService.entity.SessionStatus;
import com.bharat.EcomUserAuthService.entity.User;
import com.bharat.EcomUserAuthService.repository.SessionRepository;
import com.bharat.EcomUserAuthService.repository.UserRepository;
import com.bharat.EcomUserAuthService.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SecretKey secret;

    @Autowired
    private KafkaProducerClient kafkaProducerClient;

    @Autowired
    private ObjectMapper objectMapper;
    public UserResponseDTO login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmailId(email);

        if (userOptional.isEmpty()) {
            return null;
        }
        User user = userOptional.get();
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Wrong username password");
        }

        String token = RandomStringUtils.randomAlphanumeric(30);
        MacAlgorithm alg = Jwts.SIG.HS256; //or HS384 or HS256
        SecretKey key = alg.key().build();

        Map<String, Object>  jsonForJwt = new HashMap<>();
        jsonForJwt.put("email", user.getEmailId());
        jsonForJwt.put("createdAt", new Date());
        jsonForJwt.put("expiryAt", new Date(LocalDate.now().plusDays(3).toEpochDay()));

        token = Jwts.builder()
                .claims(jsonForJwt)
                .signWith(key, alg)
                .compact();

        Session session = new Session();
        session.setSessionStatus(SessionStatus.Active);
        session.setToken(token);
        session.setUser(user);
        sessionRepository.save(session);

        UserResponseDTO userDto = UserResponseDTO.from(user);
        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token:" + token);

        ResponseEntity<UserResponseDTO> response = new ResponseEntity<>(userDto, headers, HttpStatus.OK);
        return response.getBody();
    }

    public ResponseEntity<Void> logout(String token, Long userId) {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);
        if (sessionOptional.isEmpty()) {
            return null;
        }
        Session session = sessionOptional.get();
        session.setSessionStatus(SessionStatus.Inactive);
        sessionRepository.save(session);
        return ResponseEntity.ok().build();
    }

    public UserResponseDTO signUp(String email, String password) {
        User user = new User();
        user.setEmailId(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        User savedUser = userRepository.save(user);

        UserResponseDTO userDto = UserResponseDTO.from(savedUser);

        try {
            kafkaProducerClient.sendMessage("userSignUp", objectMapper.writeValueAsString(userDto));

            SendMessageDTO emailMessage = new SendMessageDTO();
            emailMessage.setTo(userDto.getEmail());
            emailMessage.setFrom("bharat01rajput@gmail.com.com");
            emailMessage.setSubject("Hi this is Bharat Tanwar");
            emailMessage.setBody("Thanks for creating an account. looking forward to see you");
            kafkaProducerClient.sendMessage("sendEmail", objectMapper.writeValueAsString(emailMessage));
        } catch (Exception e) {
            System.out.println("Something has gone wrong");
        }
        return userDto;
    }

    public SessionStatus validate(String token, Long userId) {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

        if (sessionOptional.isEmpty()) {
            return SessionStatus.Inactive;
        }
        Session session = sessionOptional.get();

        if (!session.getSessionStatus().equals(SessionStatus.Active)) {
            return SessionStatus.Inactive;
        }
        Jws<Claims> claimsJws = Jwts.parser()
                .build()
                .parseSignedClaims(token);

        String email = (String) claimsJws.getPayload().get("email");
        List<Role> roles = (List<Role>) claimsJws.getPayload().get("roles");
        Date createdAt = (Date) claimsJws.getPayload().get("createdAt");

        if (createdAt.before(new Date())) {
            return SessionStatus.Inactive;
        }
        return SessionStatus.Active;
    }

}
