package com.training.project.efruitcrush.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.project.efruitcrush.model.Order;
import com.training.project.efruitcrush.model.User;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	public List<Order> findByUser(User user);
}
