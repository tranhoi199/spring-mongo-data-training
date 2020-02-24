package com.pycogroup.superblog.repository;

import com.pycogroup.superblog.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
	User findByEmail(String email);
}
