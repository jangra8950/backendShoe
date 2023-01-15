package com.training.project.efruitcrush.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
	private int orderId;
	private UserDto user;
	private int totalPrice;
	private String status;
	private String fruits;
}
