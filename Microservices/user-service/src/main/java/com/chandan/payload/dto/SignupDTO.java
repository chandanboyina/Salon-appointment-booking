package com.chandan.payload.dto;

import com.chandan.domain.UserRole;
import lombok.Data;

@Data
public class SignupDTO {
    private String email;
    private String password;
    private String phone;
    private String fullName;
    private String firstName;
    private String lastName;
    private String username;
    private UserRole role;

}
