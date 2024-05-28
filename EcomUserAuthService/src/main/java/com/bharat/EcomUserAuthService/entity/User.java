package com.bharat.EcomUserAuthService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity(name = "Ecom_User")
@Getter
@Setter
public class User extends BaseModel{
    private String name;
    private String emailId;
    private String password;
    @ManyToMany
    private List<Role> roles;
}
