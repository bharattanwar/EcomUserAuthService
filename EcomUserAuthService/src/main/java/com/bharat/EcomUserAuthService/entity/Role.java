package com.bharat.EcomUserAuthService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "ECOM_ROLE")
public class Role extends BaseModel {
    private String roleName;
    private String description;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
