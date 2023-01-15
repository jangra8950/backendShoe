package com.training.project.efruitcrush.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {
	private String username;
	private String emailId;
	private String password;
	private String mobileNumber;
	private String role;
}
