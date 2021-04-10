package com.pycogroup.superblog.service;

import com.pycogroup.superblog.exception.AlreadyCreateUser;
import com.pycogroup.superblog.exception.EmailNotFound;
import com.pycogroup.superblog.exception.UserIdNotFound;
import com.pycogroup.superblog.model.QUser;
import com.pycogroup.superblog.model.User;
import com.pycogroup.superblog.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
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
		User findUser = userRepository.findUsersByEmail(user.getEmail());
		if(findUser != null){
			throw new AlreadyCreateUser(user.getEmail());
		}
		return userRepository.save(user);
	}

	@Override
	public User findUserByEmail(String email) {
		User users = userRepository.findUsersByEmail(email);
		if(users == null){
			throw new EmailNotFound(email);
		}
		return userRepository.findUsersByEmail(email);
	}

	@Override
	public User findUserById(ObjectId id) {
		User user = userRepository.findUserById(id);
		if(user == null){
			throw new UserIdNotFound(id.toString());
		}
		return user;
	}

	@Override
	public List<User> findUsersByPrefixName(String prefix) {
		QUser userQuery = QUser.user;

		return (List<User>) userRepository.findAll(userQuery.name.startsWith(prefix));
	}
}
