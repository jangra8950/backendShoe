package com.training.project.efruitcrush.service;

import java.io.IOException;

import com.training.project.efruitcrush.exception.EmailExistException;
import com.training.project.efruitcrush.exception.UserNotFoundException;
import com.training.project.efruitcrush.exception.UsernameExistException;
import com.training.project.efruitcrush.model.User;

public interface UserService {

	User register(User user) throws UserNotFoundException, UsernameExistException, EmailExistException;

	User findUserByUsername(String username);

	User findUserByEmailId(String emailId);

	User addNewUser(User user) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException;

	void deleteUser(int id) throws UserNotFoundException;
}
