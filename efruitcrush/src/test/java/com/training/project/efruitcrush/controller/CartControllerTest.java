package com.training.project.efruitcrush.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.training.project.efruitcrush.model.Cart;
import com.training.project.efruitcrush.model.User;
import com.training.project.efruitcrush.repository.CartRepository;
import com.training.project.efruitcrush.service.UserService;

@TestMethodOrder(OrderAnnotation.class)
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class CartControllerTest {

	private static MockHttpServletRequest request;

	@Autowired
	private UserService userService;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private JdbcTemplate jdbc;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	public static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;

	public static final String JWT_TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJhdWQiOiJFLUZydWl0IENydXNoIiwic3ViIjoidXNlciIsImlzcyI6IkdldCBBcnJheXMsIExMQyIsImV4cCI6MTY2MTc1NzE3MCwiaWF0IjoxNjYxMzI1MTcwLCJhdXRob3JpdGllcyI6WyJmcnVpdDpyZWFkLCBjYXJ0OnJlYWQsIGNhcnQ6dXBkYXRlLCBjYXJ0OndyaXRlLCBvcmRlcjpyZWFkLCBvcmRlcjp3cml0ZSwgb3JkZXI6ZGVsZXRlIl19.stM7rUW-S2YLLAmjAJj3RtPs2JZyoT9nMCFzn6lAQazighca3yMQ51YBdh_Yk-HoCH2O1W3a4KbBjm21GLuYYw";

	@BeforeAll
	public static void setup() {
		request = new MockHttpServletRequest();
	}

	@Test
	@DisplayName("Get all cart items")
	void getAllCartItems() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/cart").header(HttpHeaders.AUTHORIZATION, JWT_TOKEN))
				.andExpect(status().isOk()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(3)));
//    	.andDo(print());
	}

	@Test
	@DisplayName("Add a cart item")
	void addCartItem() throws Exception {

		User user = userService.findUserByUsername("user");
		Cart newCart = new Cart();
		newCart.setFruitName("Apple");
		newCart.setPrice(50);
		newCart.setQuantity(1);
		newCart.setTotalPrice(50);
		newCart.setUser(user);
		newCart.setImageUrl(
				"https://media.istockphoto.com/photos/apple-isolated-on-wood-background-picture-id614871876?k=20&m=614871876&s=612x612&w=0&h=QBNgcCux5YUpa4-LxK6kfvlNPHi26P3mP563Z_SG_Mg=");
		mockMvc.perform(post("/cart").header(HttpHeaders.AUTHORIZATION, JWT_TOKEN).contentType(APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(newCart))).andExpect(status().isCreated())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8)).andExpect(jsonPath("$.fruitName", is("Apple")))
				.andDo(print());
		// optional layer of assertion
//    	Cart verifyNewCart = cartRepository.getById(141);
//    	assertEquals(verifyNewCart.getId(), 141, "Cart items mismatched");
	}

	@Test
	@DisplayName("Update a cart item")
	void updateCartItem() throws Exception {

		User user = userService.findUserByUsername("user");
		Cart updatedCart = new Cart();
		updatedCart.setQuantity(3);
		updatedCart.setTotalPrice(150);
		updatedCart.setUser(user);
		updatedCart.setImageUrl(
				"https://media.istockphoto.com/photos/apple-isolated-on-wood-background-picture-id614871876?k=20&m=614871876&s=612x612&w=0&h=QBNgcCux5YUpa4-LxK6kfvlNPHi26P3mP563Z_SG_Mg=");
		mockMvc.perform(MockMvcRequestBuilders.put("/cart/190").header(HttpHeaders.AUTHORIZATION, JWT_TOKEN)
				.contentType(APPLICATION_JSON_UTF8).content(objectMapper.writeValueAsString(updatedCart)))
				.andExpect(status().isOk()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.quantity", is(3)));
//    			.andDo(print());
	}

	@Test
	@DisplayName("Delete a cart item")
	void deleteCart() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/cart/190").header(HttpHeaders.AUTHORIZATION, JWT_TOKEN)
				.contentType(APPLICATION_JSON_UTF8)).andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8)).andExpect(jsonPath("$.status", is("OK")))
				.andExpect(jsonPath("$.message", is("Cart item deleted successfully")));
//		.andDo(print());

	}

}
