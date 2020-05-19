package com.eyasu.todo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.eyasu.todo.domain.User;
import com.eyasu.todo.sevice.UserRepository;

@Controller
public class SecurityController {
	
	@Autowired
	private BCryptPasswordEncoder bCryptEncoder;
	
	@Autowired
	private UserRepository repository;
	
	@GetMapping("/register")
	public String register(Model model) {
		User user = new User();
		
		model.addAttribute(user);
		
		return "security/register";
		
	}
	
	@PostMapping("/register/save")
	public String saveUser(@ModelAttribute("user") User user) {
		user.setPassword(bCryptEncoder.encode(user.getPassword()));
		user.setRole("user");
		user.setEnabled(true);
		repository.save(user);
		System.out.println("User saved");
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String login() {
		
		return "security/login";
	}
		
}