package com.chandan.payload.request;

import com.chandan.domain.UserRole;
import lombok.Data;

@Data
public class SignupDto {
	private String email;
	private String password;
	private String phone;
	private String fullName;
	private UserRole role;
}