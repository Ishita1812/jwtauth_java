package com.ust.userrestJWTauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ust.userrestJWTauth.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	public User findByUsername(String username);
}
