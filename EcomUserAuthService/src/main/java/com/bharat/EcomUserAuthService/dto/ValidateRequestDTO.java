package com.bharat.EcomUserAuthService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateRequestDTO {
    private String token;//try to use the Same variables
    private Long userId;
}
