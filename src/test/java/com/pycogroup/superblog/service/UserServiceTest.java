package com.pycogroup.superblog.service;

import com.pycogroup.superblog.exception.AlreadyCreateUser;
import com.pycogroup.superblog.exception.EmailNotFound;
import com.pycogroup.superblog.exception.UserIdNotFound;
import com.pycogroup.superblog.model.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.bson.types.ObjectId;
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
			.name("A" + RandomStringUtils.random(40))
			.email(TEST_USER_EMAIL)
			.build());
		mongoTemplate.save(User.builder()
			.name("A" + RandomStringUtils.random(40))
			.email("A" + RandomStringUtils.random(40) + "@local.email")
			.build());
		mongoTemplate.save(User.builder()
			.name("B" + RandomStringUtils.random(40))
			.email("B" + RandomStringUtils.random(40) + "@local.email")
			.build());

	}

	@Test
	public void testFindAllMustReturnEnoughQuantity() {
		List<User> userList = userService.getAllUsers();
		Assert.assertEquals(3, userList.size());
	}

	@Test
	public void testFindByExistEmailMustReturnResult() {
		User user = userService.findUserByEmail(TEST_USER_EMAIL);
		Assert.assertNotNull(user);
		Assert.assertEquals(TEST_USER_EMAIL, user.getEmail());
	}

	@Test
	public void testFindUsersByPrefixNameMustReturnCorrectResult() {
		List<User> userList = userService.findUsersByPrefixName("A");
		Assert.assertEquals(2, userList.size());
		for (User user: userList) {
			Assert.assertTrue(user.getName().startsWith("A"));
		}
	}

	@Test
	public void testFindUSerByIdReturnUser(){
		List<User> userList = userService.getAllUsers();
		User user = userList.get(0);
		ObjectId id = userList.get(0).getId();
		User findUser = userService.findUserById(id);
		Assert.assertEquals(id.toString(), findUser.getId().toString());
	}
	@Test
	public void testCreateUSer(){
		User user = User.builder()
				.name(RandomStringUtils.random(10))
				.email(RandomStringUtils.random(20))
				.build();
		userService.createUser(user);
		Assert.assertEquals(4, userService.getAllUsers().size());
	}

	@Test(expected = AlreadyCreateUser.class)
	public void testCreateDuplicateUserThrowException(){
		User user = User.builder()
				.name(RandomStringUtils.random(10))
				.email(RandomStringUtils.random(20))
				.build();
		userService.createUser(user);
		userService.createUser(user);
	}

	@Test(expected = UserIdNotFound.class)
	public void findUserByInvalidId(){
		userService.findUserById(ObjectId.get());
	}

	@Test(expected = EmailNotFound.class)
	public void findUserByInvalidIdThrowException(){
		userService.findUserByEmail(RandomStringUtils.random(10));
	}
}
