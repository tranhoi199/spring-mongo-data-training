package com.pycogroup.superblog.repository;

import com.pycogroup.superblog.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String>,
								CustomUserRepository,
								QuerydslPredicateExecutor<User> {
	User findByEmail(String email);
	@Query("{'address.country': ?0}")
	List<User> findByCountry(final String country);
}
