package com.training.project.efruitcrush.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.training.project.efruitcrush.model.Order;
import com.training.project.efruitcrush.model.User;
import com.training.project.efruitcrush.service.UserService;

@TestMethodOrder(OrderAnnotation.class)
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class OrderControllerTest {

	private static MockHttpServletRequest request;

	@Autowired
	private UserService userService;

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
	@DisplayName("Get all orders")
	void getAllOrders() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/orders").header(HttpHeaders.AUTHORIZATION, JWT_TOKEN))
				.andExpect(status().isOk()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(6)));
//    	.andDo(print());
	}

	@Test
	@DisplayName("Add an order")
	void addOrder() throws Exception {

		User user = userService.findUserByUsername("user");
		Order newOrder = new Order();
		newOrder.setTotalPrice(66);
		newOrder.setStatus("order placed");
		newOrder.setUser(user);
		newOrder.setFruits("[{\"id\":37,\"name\":\"Kiwi\",\"quantity\":1,\"unitPrice\":60,\"subtotal\":60}]\"}");
		mockMvc.perform(post("/orders").header(HttpHeaders.AUTHORIZATION, JWT_TOKEN).contentType(APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(newOrder))).andExpect(status().isCreated())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.status", is("order placed")));
//    			.andDo(print());
	}

	@Test
	@DisplayName("Delete an order")
	void deleteOrder() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/orders/144").header(HttpHeaders.AUTHORIZATION, JWT_TOKEN)
				.contentType(APPLICATION_JSON_UTF8)).andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8)).andExpect(jsonPath("$.status", is("OK")))
				.andExpect(jsonPath("$.message", is("Order deleted successfully")));
//		.andDo(print());

	}

}
