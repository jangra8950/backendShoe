package com.training.project.efruitcrush.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderId;

	@JsonIgnore
//	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	private int totalPrice;
	private String status;
	private String shoes;

	public Order() {
	}

	public Order(int totalPrice, String status, String shoe) {
		super();
		this.totalPrice = totalPrice;
		this.status = status;
		this.shoes = shoe;
	}

}
