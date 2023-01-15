package com.training.project.efruitcrush.dto;

import com.training.project.efruitcrush.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
	private int id;
	private String fruitName;
	private int quantity;
	private int price;
	private int totalPrice;
	private String imageUrl;
	private User user;

}
