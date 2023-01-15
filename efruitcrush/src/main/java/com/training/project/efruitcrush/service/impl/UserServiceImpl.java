package com.training.project.efruitcrush.service.impl;

import static com.training.project.efruitcrush.constant.UserConstant.EMAIL_ALREADY_EXISTS;
import static com.training.project.efruitcrush.constant.UserConstant.FOUND_USER_BY_USERNAME;
import static com.training.project.efruitcrush.constant.UserConstant.NO_USER_FOUND_BY_USERNAME;
import static com.training.project.efruitcrush.constant.UserConstant.USERNAME_ALREADY_EXISTS;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.training.project.efruitcrush.enumeration.Role;
import com.training.project.efruitcrush.exception.EmailExistException;
import com.training.project.efruitcrush.exception.UserNotFoundException;
import com.training.project.efruitcrush.exception.UsernameExistException;
import com.training.project.efruitcrush.model.User;
import com.training.project.efruitcrush.model.UserPrincipal;
import com.training.project.efruitcrush.repository.UserRepository;
import com.training.project.efruitcrush.service.LoginAttemptService;
import com.training.project.efruitcrush.service.UserService;

@Service
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {
	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	private UserRepository userRepository;
	private BCryptPasswordEncoder passwordEncoder;
	private LoginAttemptService loginAttemptService;

	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
			LoginAttemptService loginAttemptService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.loginAttemptService = loginAttemptService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			LOGGER.error(NO_USER_FOUND_BY_USERNAME + username);
			throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + username);
		} else {
			validateLoginAttempt(user);
			userRepository.save(user);
			UserPrincipal userPrincipal = new UserPrincipal(user);
			LOGGER.info(FOUND_USER_BY_USERNAME + username);
			return userPrincipal;
		}
	}

	@Override
	public User register(User newUser) throws UserNotFoundException, UsernameExistException, EmailExistException {
		validateNewUsernameAndEmail(EMPTY, newUser.getUsername(), newUser.getEmailId());
		return addNewUser(newUser);
	}

	@Override
	public User addNewUser(User newUser) throws UserNotFoundException, UsernameExistException, EmailExistException {
		validateNewUsernameAndEmail(EMPTY, newUser.getUsername(), newUser.getEmailId());
		User user = new User();
		user.setUsername(newUser.getUsername());
		user.setEmailId(newUser.getEmailId());
		user.setPassword(encodePassword(newUser.getPassword()));
		user.setMobileNumber(newUser.getMobileNumber());
		user.setUserCart(newUser.getUserCart());
		user.setUserOrders(newUser.getUserOrders());
		user.setRole(getRoleEnumName(newUser.getRole()).name());
		user.setAuthorities(getRoleEnumName(newUser.getRole()).getAuthorities());
		userRepository.save(user);
		return user;
	}

	@Override
	public User findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User findUserByEmailId(String emailId) {
		return userRepository.findByEmailId(emailId);
	}

	private Role getRoleEnumName(String role) {
		return Role.valueOf(role.toUpperCase());
	}

	private String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}

	private void validateLoginAttempt(User user) {
		if (user.isNotLocked()) {
			if (loginAttemptService.hasExceededMaxAttempts(user.getUsername())) {
				user.setNotLocked(false);
			} else {
				user.setNotLocked(true);
			}
		} else {
			loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
		}
	}

	private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmailId)
			throws UserNotFoundException, UsernameExistException, EmailExistException {
		User userByNewUsername = findUserByUsername(newUsername);
		User userByNewEmailId = findUserByEmailId(newEmailId);
		if (StringUtils.isNotBlank(currentUsername)) {
			User currentUser = findUserByUsername(currentUsername);
			if (currentUser == null) {
				throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME + currentUsername);
			}
			if (userByNewUsername != null && currentUser.getId() != userByNewUsername.getId()) {
				throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
			}
			if (userByNewEmailId != null && currentUser.getId() != userByNewEmailId.getId()) {
				throw new EmailExistException(EMAIL_ALREADY_EXISTS);
			}
			return currentUser;
		} else {
			if (userByNewUsername != null) {
				throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
			}
			if (userByNewEmailId != null) {
				throw new EmailExistException(EMAIL_ALREADY_EXISTS);
			}
			return null;
		}
	}

	@Override
	public void deleteUser(int id) throws UserNotFoundException {
		userRepository.deleteById(id);
	}

}
