package com.training.project.efruitcrush.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "cart")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@JsonIgnore
//	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	private String shoeName;
	private int quantity;
	private int price;
	private int totalPrice;
	private String imageUrl;

	public Cart() {
	}

	public Cart(String shoeName, int quantity, int price, int totalPrice, String imageUrl) {
		super();
		this.shoeName = shoeName;
		this.quantity = quantity;
		this.price = price;
		this.totalPrice = totalPrice;
		this.imageUrl = imageUrl;
	}

}
