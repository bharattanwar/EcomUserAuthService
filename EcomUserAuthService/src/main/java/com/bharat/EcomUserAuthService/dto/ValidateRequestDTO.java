package com.bharat.EcomUserAuthService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateRequestDTO {
    String token;//try to use the Same variables
    Long userId;
}
