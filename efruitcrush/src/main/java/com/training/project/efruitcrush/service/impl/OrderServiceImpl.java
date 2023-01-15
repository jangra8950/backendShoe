package com.training.project.efruitcrush.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.project.efruitcrush.exception.OrderNotFoundException;
import com.training.project.efruitcrush.model.Order;
import com.training.project.efruitcrush.model.User;
import com.training.project.efruitcrush.repository.OrderRepository;
import com.training.project.efruitcrush.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	public OrderRepository orderRepository;

	@Override
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	@Override
	public List<Order> getAllOrdersByUser(User user) {
		return orderRepository.findByUser(user);
	}

	@Override
	public Order getOrderById(int orderId) throws OrderNotFoundException {
		return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found"));
	}

	@Override
	public Order addOrder(Order order) {
		return orderRepository.save(order);
	}

	@Override
	public void deleteOrder(int orderId) {
		orderRepository.deleteById(orderId);
	}

}
