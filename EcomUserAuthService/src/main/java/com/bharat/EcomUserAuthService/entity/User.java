package com.bharat.EcomUserAuthService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity(name = "ECOM_USER")
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseModel {
    private String name;
    private String emailId;
    private String password;
    private String token;

    @ManyToMany
    private List<Role> roles;
}
