package com.training.project.efruitcrush.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.project.efruitcrush.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByEmailId(String emailId);

	public User findByUsername(String username);
}
