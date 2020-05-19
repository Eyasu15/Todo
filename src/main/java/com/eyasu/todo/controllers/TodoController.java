package com.eyasu.todo.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.eyasu.todo.domain.Todo;
import com.eyasu.todo.domain.User;
import com.eyasu.todo.sevice.TodoRepository;
import com.eyasu.todo.sevice.UserRepository;

@Controller
@SessionAttributes("user")
public class TodoController {

	@Autowired
	private TodoRepository todoRepository;
	
	@Autowired
	private UserRepository repository;
	
	@GetMapping("/show")
	public String showTodos(Authentication auth, Model model) {
		
		User user = repository.findOneByEmail(auth.getName());
		
		List<Todo> todos = todoRepository.findByUserId(user.getId());
		
		model.addAttribute("myTodos", todos);
		
		return "user-homepage";
	}
	

	@PostMapping("/{action}/save")
	public String saveTodo(@Valid @ModelAttribute Todo todo, BindingResult result, @PathVariable String action, Authentication auth) {
		
		if(result.hasErrors()) {
			System.out.println("We have errors=============");
			return action+"_todo";
		}
		
		User user = repository.findOneByEmail(auth.getName());
		
		todo.setUser(user);
		todo.setDateAdded(LocalDate.now());
		
		todoRepository.save(todo);
		
		return "redirect:/show";
	}
	
	

	@RequestMapping("/new")
	public String addNewTodo(@ModelAttribute Todo todo, Model model) {
				
		model.addAttribute("todo", todo);
		
		return "add_todo";
	}
	
	@RequestMapping("/edit/{id}")
	public String editTodo(@PathVariable Long id, Model model) {
		
		Optional<Todo> todo = todoRepository.findById(id);
	
		
		model.addAttribute("todo", todo);
		
		return "edit_todo";
	}
	

	@RequestMapping("/delete/{id}")
	public String delted(@PathVariable Long id) {
		todoRepository.deleteById(id);
		
		return "redirect:/show";
	}
}
