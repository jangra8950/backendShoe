package com.training.project.efruitcrush.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "shoes")
public class Shoes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private int price;
	private int quantityAvailable;
	private String imageUrl;

	public Shoes() {
	}

	public Shoes(String name, int price, int quantityAvailable, String imageUrl) {
		super();
		this.name = name;
		this.price = price;
		this.quantityAvailable = quantityAvailable;
		this.imageUrl = imageUrl;
	}

}
