package com.training.project.efruitcrush.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String emailId;
	private String password;
	private String mobileNumber;
	private String role;
	private String[] authorities;
	private boolean isActive;
	private boolean isNotLocked;

//	@JsonIgnore
//	@JsonBackReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
	private List<Order> userOrders;

//	@JsonIgnore
//	@JsonBackReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
	private List<Cart> userCart;

	public User() {
	}

	public User(String username, String emailId, String password, String mobileNumber, String role, boolean isActive,
			boolean isNotLocked) {
		super();
		this.username = username;
		this.emailId = emailId;
		this.password = password;
		this.mobileNumber = mobileNumber;
		this.role = role;
		if (this.role.equals("role_user")) {
			String[] userAuthoritites = { "fruit:read, cart:read, cart:update, cart:write, order:read, order:write" };
			this.setAuthorities(userAuthoritites);
		} else if (this.role.equals("role_admin")) {
			String[] adminAuthoritites = { "fruit:read, fruit:write, cart:update, order:read" };
			this.setAuthorities(adminAuthoritites);
		}
	}

}
