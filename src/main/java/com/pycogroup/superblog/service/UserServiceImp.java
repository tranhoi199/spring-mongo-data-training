package com.pycogroup.superblog.service;

import com.pycogroup.superblog.model.User;
import com.pycogroup.superblog.repository.ArticleRepository;
import com.pycogroup.superblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User createUser(User user) {
		return userRepository.save(user);
	}
}
