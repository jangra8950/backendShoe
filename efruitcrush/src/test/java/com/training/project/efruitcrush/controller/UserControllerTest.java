package com.training.project.efruitcrush.controller;

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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.project.efruitcrush.dto.LoginRequestDto;
import com.training.project.efruitcrush.dto.SignupRequestDto;

@TestMethodOrder(OrderAnnotation.class)
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class UserControllerTest {

	private static MockHttpServletRequest request;

	@PersistenceContext
	private EntityManager entityManager;

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
	@DisplayName("Signup")
	void signup() throws Exception {
		SignupRequestDto signupRequestDto = new SignupRequestDto();
		signupRequestDto.setUsername("test admin1");
		signupRequestDto.setEmailId("testadmin1@gmail.com");
		signupRequestDto.setPassword("testadmin1@gmail.com");
		signupRequestDto.setMobileNumber("1234567890");
		signupRequestDto.setRole("role_admin");
		mockMvc.perform(post("/signup").contentType(APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(signupRequestDto))).andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8)).andExpect(jsonPath("$.status", is("OK")))
				.andExpect(jsonPath("$.message", is("User registered successfully with username:  test admin1")));
//    	.andDo(print());
	}

	@Test
	@DisplayName("Login")
	void login() throws Exception {
		LoginRequestDto loginRequestDto = new LoginRequestDto();
		loginRequestDto.setEmailId("admin@gmail.com");
		loginRequestDto.setPassword("admin@gmail.com");
		mockMvc.perform(post("/login").contentType(APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(loginRequestDto))).andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.emailId", is("admin@gmail.com")));
//    	.andDo(print());

	}

}
