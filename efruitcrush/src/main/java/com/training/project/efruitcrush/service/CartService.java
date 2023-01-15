package com.training.project.efruitcrush.service;

import java.util.List;

import com.training.project.efruitcrush.exception.CartItemNotFoundException;
import com.training.project.efruitcrush.model.Cart;

public interface CartService {

	public Cart getCartItemById(int cartItemId) throws CartItemNotFoundException;

	public List<Cart> getAllCartItems();

	public Cart addCartItem(Cart cartItem);

	public Cart updateCartItem(int cartItemId, Cart cartItem) throws CartItemNotFoundException;

	public void deleteCartItem(int cartItemId);

}
