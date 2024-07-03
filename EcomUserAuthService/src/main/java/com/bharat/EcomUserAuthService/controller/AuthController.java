package com.bharat.EcomUserAuthService.controller;

import com.bharat.EcomUserAuthService.dto.*;
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
    public ResponseEntity<UserDTO> signUp(@RequestBody SignupRequestDTO signupRequestDTO){
        try{
            User user=authService.signUp(signupRequestDTO.getEmail(),signupRequestDTO.getPassword());
            UserDTO userDTO=getUserDTO(user);
            return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("auth/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        try{
            Pair<User, MultiValueMap<String,String>>BodyWithHeaders= authService.login(loginRequestDTO.getEmail(),loginRequestDTO.getPassword());
            UserDTO userDTO=getUserDTO(BodyWithHeaders.a);
            //BodyWithHeaders.a-> this will give us User
            return new ResponseEntity<>(userDTO,BodyWithHeaders.b, HttpStatus.OK);
            //BodyWithHeaders.b-> Headers
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/auth/validate")
    public ResponseEntity<Boolean>validate(@RequestBody ValidateRequestDTO validateRequestDTO){
        Boolean isValid=authService.validate(validateRequestDTO.getToken(),validateRequestDTO.getUserId());
        return new ResponseEntity<>(isValid,HttpStatus.OK);
    }


    public UserDTO getUserDTO(User user){
        UserDTO userDTO=new UserDTO();
        userDTO.setEmail(user.getEmail());
        // userDTO.setRoles(user.getRoleList());
        return userDTO;
    }
}