package com.training.project.efruitcrush.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
	private int id;
	private String username;
	private String emailId;
	private String mobileNumber;
	private String role;
	private boolean isActive;
	private boolean isNotLocked;
}
