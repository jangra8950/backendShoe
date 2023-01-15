package com.training.project.efruitcrush.service;

import java.util.List;

import com.training.project.efruitcrush.exception.OrderNotFoundException;
import com.training.project.efruitcrush.model.Order;
import com.training.project.efruitcrush.model.User;

public interface OrderService {

	public List<Order> getAllOrders();

	public List<Order> getAllOrdersByUser(User user);

	public Order getOrderById(int orderId) throws OrderNotFoundException;

	public Order addOrder(Order order);

	public void deleteOrder(int orderId);

}
