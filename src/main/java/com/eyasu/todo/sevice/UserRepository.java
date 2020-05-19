package com.eyasu.todo.sevice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eyasu.todo.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	User findOneByEmail(String email);
	
}
