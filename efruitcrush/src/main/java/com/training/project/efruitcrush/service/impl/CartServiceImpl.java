package com.training.project.efruitcrush.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.project.efruitcrush.exception.CartItemNotFoundException;
import com.training.project.efruitcrush.model.Cart;
import com.training.project.efruitcrush.repository.CartRepository;
import com.training.project.efruitcrush.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	public CartRepository cartRepository;

	@Override
	public List<Cart> getAllCartItems() {
		return cartRepository.findAll();
	}

	@Override
	public Cart getCartItemById(int cartItemId) throws CartItemNotFoundException {
		return cartRepository.findById(cartItemId)
				.orElseThrow(() -> new CartItemNotFoundException("Cart item not found"));
	}

	@Override
	public Cart addCartItem(Cart cartItem) {
		return cartRepository.save(cartItem);
	}

	@Override
	public Cart updateCartItem(int cartItemId, Cart cartItem) throws CartItemNotFoundException {
		Cart existingCartItem = cartRepository.findById(cartItemId)
				.orElseThrow(() -> new CartItemNotFoundException("Cart item not found"));
		existingCartItem.setFruitName(cartItem.getFruitName());
		existingCartItem.setPrice(cartItem.getPrice());
		existingCartItem.setQuantity(cartItem.getQuantity());
		existingCartItem.setTotalPrice(cartItem.getTotalPrice());
		existingCartItem.setImageUrl(cartItem.getImageUrl());
		return cartRepository.save(existingCartItem);
	}

	@Override
	public void deleteCartItem(int cartItemId) {
		cartRepository.deleteById(cartItemId);
	}

}
