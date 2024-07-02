package com.bharat.EcomUserAuthService.dto;

import com.bharat.EcomUserAuthService.entity.SessionStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateTokenResponseDto {
    private UserDto userDto;
    private SessionStatus sessionStatus;
}