package com.pycogroup.superblog.controller;

import com.pycogroup.superblog.model.User;
import com.pycogroup.superblog.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@GetMapping(value = "/")
	public List<User> getAllUsers() {
		return Collections.emptyList();
	}
	@PostMapping(value = "/")
	public User createUser(@RequestBody User user) {
		log.info("Saving user.");
		return userRepository.save(user);
	}
}
