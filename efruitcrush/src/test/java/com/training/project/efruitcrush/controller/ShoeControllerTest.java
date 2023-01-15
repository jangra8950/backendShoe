package com.training.project.efruitcrush.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
import com.training.project.efruitcrush.model.Shoes;

@TestMethodOrder(OrderAnnotation.class)
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class ShoeControllerTest {

	private static MockHttpServletRequest request;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private JdbcTemplate jdbc;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	public static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;

	@BeforeAll
	public static void setup() {
		request = new MockHttpServletRequest();
	}

	@Test
	@DisplayName("Get all fruits")
	void getAllFruits() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/fruits").header(HttpHeaders.AUTHORIZATION,
				"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJhdWQiOiJFLUZydWl0IENydXNoIiwic3ViIjoiYWRtaW4iLCJpc3MiOiJHZXQgQXJyYXlzLCBMTEMiLCJleHAiOjE2NjE3MDcxNDksImlhdCI6MTY2MTI3NTE0OSwiYXV0aG9yaXRpZXMiOlsiZnJ1aXQ6cmVhZCwgZnJ1aXQ6d3JpdGUsIGZydWl0OnVwZGF0ZSwgb3JkZXI6cmVhZCJdfQ.GW4uC-QKwPjpyqxCh8ngufAV8w2hXdeS5_56ZDdFJSZn0zwL51cCYf-w6OEl1g9m0x4QTc_eGGSqJm1T-b7Nvw"))
				.andExpect(status().isOk()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(6)));
//    	.andDo(print());
	}

	@Test
	@DisplayName("Add a fruit")
	void addFruit() throws Exception {

		Shoes newFruit = new Shoes();
		newFruit.setName("Apple");
		newFruit.setPrice(50);
		newFruit.setQuantityAvailable(25);
		newFruit.setImageUrl(
				"https://media.istockphoto.com/photos/apple-isolated-on-wood-background-picture-id614871876?k=20&m=614871876&s=612x612&w=0&h=QBNgcCux5YUpa4-LxK6kfvlNPHi26P3mP563Z_SG_Mg=");
		mockMvc.perform(post("/admin/fruits/addFruit").header(HttpHeaders.AUTHORIZATION,
				"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJhdWQiOiJFLUZydWl0IENydXNoIiwic3ViIjoiYWRtaW4iLCJpc3MiOiJHZXQgQXJyYXlzLCBMTEMiLCJleHAiOjE2NjE3MDcxNDksImlhdCI6MTY2MTI3NTE0OSwiYXV0aG9yaXRpZXMiOlsiZnJ1aXQ6cmVhZCwgZnJ1aXQ6d3JpdGUsIGZydWl0OnVwZGF0ZSwgb3JkZXI6cmVhZCJdfQ.GW4uC-QKwPjpyqxCh8ngufAV8w2hXdeS5_56ZDdFJSZn0zwL51cCYf-w6OEl1g9m0x4QTc_eGGSqJm1T-b7Nvw")
				.contentType(APPLICATION_JSON_UTF8).content(objectMapper.writeValueAsString(newFruit)))
				.andExpect(status().isCreated()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.name", is("Apple")));
//    			.andDo(print());
	}

	@Test
	@DisplayName("Update a fruit")
	void updateFruit() throws Exception {

		Shoes updatedFruit = new Shoes();
		updatedFruit.setName("Apple fruit");
		updatedFruit.setImageUrl(
				"https://media.istockphoto.com/photos/apple-isolated-on-wood-background-picture-id614871876?k=20&m=614871876&s=612x612&w=0&h=QBNgcCux5YUpa4-LxK6kfvlNPHi26P3mP563Z_SG_Mg=");
		mockMvc.perform(MockMvcRequestBuilders.put("/admin/fruits/editFruit/33").header(HttpHeaders.AUTHORIZATION,
				"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJhdWQiOiJFLUZydWl0IENydXNoIiwic3ViIjoiYWRtaW4iLCJpc3MiOiJHZXQgQXJyYXlzLCBMTEMiLCJleHAiOjE2NjE3MDcxNDksImlhdCI6MTY2MTI3NTE0OSwiYXV0aG9yaXRpZXMiOlsiZnJ1aXQ6cmVhZCwgZnJ1aXQ6d3JpdGUsIGZydWl0OnVwZGF0ZSwgb3JkZXI6cmVhZCJdfQ.GW4uC-QKwPjpyqxCh8ngufAV8w2hXdeS5_56ZDdFJSZn0zwL51cCYf-w6OEl1g9m0x4QTc_eGGSqJm1T-b7Nvw")
				.contentType(APPLICATION_JSON_UTF8).content(objectMapper.writeValueAsString(updatedFruit)))
				.andExpect(status().isOk()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.name", is("Apple fruit")));
//    			.andDo(print());
	}

	@Test
	@DisplayName("Delete a fruit")
	void deleteFruit() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/admin/fruits/deleteFruit/33").header(HttpHeaders.AUTHORIZATION,
				"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJhdWQiOiJFLUZydWl0IENydXNoIiwic3ViIjoiYWRtaW4iLCJpc3MiOiJHZXQgQXJyYXlzLCBMTEMiLCJleHAiOjE2NjE3MDcxNDksImlhdCI6MTY2MTI3NTE0OSwiYXV0aG9yaXRpZXMiOlsiZnJ1aXQ6cmVhZCwgZnJ1aXQ6d3JpdGUsIGZydWl0OnVwZGF0ZSwgb3JkZXI6cmVhZCJdfQ.GW4uC-QKwPjpyqxCh8ngufAV8w2hXdeS5_56ZDdFJSZn0zwL51cCYf-w6OEl1g9m0x4QTc_eGGSqJm1T-b7Nvw")
				.contentType(APPLICATION_JSON_UTF8)).andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8)).andExpect(jsonPath("$.status", is("OK")))
				.andExpect(jsonPath("$.message", is("Fruit deleted successfully")));
//		.andDo(print());

	}

}
