package com.bharat.EcomUserAuthService.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ECOM_ROLE")
public class Role extends BaseModel {
    private String value;

}
