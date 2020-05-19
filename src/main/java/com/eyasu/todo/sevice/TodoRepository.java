package com.eyasu.todo.sevice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eyasu.todo.domain.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

	List<Todo> findByUserId(Long id); 
}
