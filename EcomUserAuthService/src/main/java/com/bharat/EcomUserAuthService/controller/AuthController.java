package com.bharat.EcomUserAuthService.controller;

import com.bharat.EcomUserAuthService.dto.SignupRequestDTO;
import com.bharat.EcomUserAuthService.dto.ValidateRequestDTO;
import com.bharat.EcomUserAuthService.dto.LoginRequestDTO;
import com.bharat.EcomUserAuthService.dto.UserResponseDTO;
import com.bharat.EcomUserAuthService.entity.User;
import com.bharat.EcomUserAuthService.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.antlr.v4.runtime.misc.Pair;

@RestController
public class AuthController {
    //Signup
    //login
    //ForgetPassword
    //logout
    @Autowired
    private AuthService authService;

    @PostMapping("/auth/signup")
    public ResponseEntity<UserResponseDTO> signUp(@RequestBody SignupRequestDTO signupRequestDTO){
        try{
            User user=authService.signUp(signupRequestDTO.getEmail(),signupRequestDTO.getPassword());
            UserResponseDTO userResponseDTO = getUserDTO(user);
            return new ResponseEntity<UserResponseDTO>(userResponseDTO, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("auth/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        try {
            Pair<User, MultiValueMap<String, String>> bodyWithHeaders = authService.login(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
            UserResponseDTO userDTO = getUserDTO(bodyWithHeaders.a);
            // bodyWithHeaders.getFirst() -> this will give us User
            return new ResponseEntity<>(userDTO, bodyWithHeaders.b, HttpStatus.OK);
            // bodyWithHeaders.getSecond() -> Headers
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/auth/validate")
    public ResponseEntity<Boolean>validate(@RequestBody ValidateRequestDTO validateRequestDTO){
        Boolean isValid=authService.validate(validateRequestDTO.getToken(),validateRequestDTO.getUserId());
        return new ResponseEntity<>(isValid,HttpStatus.OK);
    }


    public UserResponseDTO getUserDTO(User user){
        UserResponseDTO userDTO=new UserResponseDTO();
        userDTO.setEmail(user.getEmailId());
        // userDTO.setRoles(user.getRoleList());
        return userDTO;
    }
}
