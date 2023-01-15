package com.training.project.efruitcrush.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.project.efruitcrush.model.Cart;
import com.training.project.efruitcrush.model.User;

public interface CartRepository extends JpaRepository<Cart, Integer> {
	public Cart findByUser(User user);
}
