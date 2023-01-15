package com.training.project.efruitcrush.dto;

import java.util.List;

import com.training.project.efruitcrush.model.Cart;
import com.training.project.efruitcrush.model.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	private int id;
	private String username;
	private String emailId;
	private String mobileNumber;
	private String role;
	private List<Order> userOrders;
	private List<Cart> userCart;
	private boolean isActive;
	private boolean isNotLocked;
}
