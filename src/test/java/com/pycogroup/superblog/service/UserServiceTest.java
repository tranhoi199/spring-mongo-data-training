package com.pycogroup.superblog.service;

import com.pycogroup.superblog.model.Article;
import com.pycogroup.superblog.model.User;
import com.pycogroup.superblog.repository.ArticleRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserServiceTest {
	private static final String TEST_USER_EMAIL = "test@local";

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private UserService userService;

	@Before
	public void init() {
		mongoTemplate.remove(User.class).all();
		mongoTemplate.save(User.builder()
			.name(RandomStringUtils.random(40))
			.email(TEST_USER_EMAIL)
			.build());
	}

	@Test
	public void testFindAllMustReturnEnoughQuantity() {
		List<User> userList = userService.getAllUsers();
		Assert.assertEquals(1, userList.size());
	}

	@Test
	public void testFindByExistEmailMustReturnResult() {
		User user = userService.findUserByEmail(TEST_USER_EMAIL);
		Assert.assertNotNull(user);
		Assert.assertEquals(TEST_USER_EMAIL, user.getEmail());
	}
}
