package com.pycogroup.superblog.service;

import com.pycogroup.superblog.exception.ArticleNotFound;
import com.pycogroup.superblog.exception.CategoryRefError;
import com.pycogroup.superblog.model.Article;
import com.pycogroup.superblog.model.Category;
import com.pycogroup.superblog.model.User;
import com.pycogroup.superblog.repository.ArticleRepository;
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

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ArticleServiceTest {
	private static final int INIT_ARTICLE_NUMBER = 5;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private ArticleService articleService;

	private ArrayList<String> listOfArticleId = new ArrayList<>();

	@Before
	public void init() {
		mongoTemplate.remove(Article.class).all();
		mongoTemplate.remove(User.class).all();
		User author = mongoTemplate.save(User.builder()
			.name(RandomStringUtils.random(40))
			.email("fake"+RandomStringUtils.randomAlphabetic(5)+ "@.local")
			.build());
		Category category = Category.builder()
				.categoryname(RandomStringUtils.random(10))
				.description(RandomStringUtils.random(10))
				.build();
		mongoTemplate.save(category);
		for (int i = 0; i < INIT_ARTICLE_NUMBER; i++) {
			Article article = Article.builder()
				.content(RandomStringUtils.random(40))
				.title(RandomStringUtils.random(500))
				.author(author)
					.category(category)
				.build();
			mongoTemplate.save(article);
			listOfArticleId.add(article.getId().toString());
		}
	}

	@Test
	public void testFindAllMustReturnEnoughQuantity() {
		List<Article> articleList = articleService.getAllArticles();
		Assert.assertEquals(INIT_ARTICLE_NUMBER, articleList.size());
	}

	@Test
	public void testCreateArticle(){
		Article article = Article.builder()
				.content(RandomStringUtils.random(10))
				.title(RandomStringUtils.random(20))
				.build();
		User author = mongoTemplate.save(User.builder()
				.name(RandomStringUtils.random(40))
				.email("fake"+RandomStringUtils.randomAlphabetic(5)+ "@.local")
				.build());
		Category category = Category.builder()
				.categoryname(RandomStringUtils.random(10))
				.description(RandomStringUtils.random(10))
				.build();
		mongoTemplate.save(category);
		mongoTemplate.save(author);
//		mongoTemplate.save(article);
		articleService.createArticle(article, category.getId(), author.getId());
		Assert.assertEquals(INIT_ARTICLE_NUMBER + 1, articleService.getAllArticles().size());
	}

	@Test
	public void testAddUserToArticle(){
		User user = User.builder()
				.name(RandomStringUtils.random(10))
				.email(RandomStringUtils.random(20))
				.build();
		String id = listOfArticleId.get(0);
		Article article = articleService.findArticleById(new ObjectId(id));
		article = articleService.addUserToArticle(user, article);
		Assert.assertEquals(user.getName(), article.getAuthor().getName());
	}

	@Test(expected = ArticleNotFound.class)
	public void testFindInvalidArticleId(){
		articleService.findArticleById(ObjectId.get());
	}

	@Test
	public void testFindValidArticleId(){
		Article article = articleService.findArticleById(new ObjectId(listOfArticleId.get(0)));
		Assert.assertEquals(listOfArticleId.get(0), article.getId().toString());
	}

	@Test
	public void testDeleteValidArticleId(){
		Article article = articleService.findArticleById(new ObjectId(listOfArticleId.get(0)));
		articleService.findAndDeleteArticleById(article.getId());
		Assert.assertEquals(INIT_ARTICLE_NUMBER - 1, articleService.getAllArticles().size());
	}

	@Test(expected = ArticleNotFound.class)
	public void testDeleteInvalidArticleIdThrowException(){
		articleService.findAndDeleteArticleById(ObjectId.get());
	}

	@Test
	public void testUpdateArticleWithValidId(){
		String content = RandomStringUtils.random(10);
		String title = RandomStringUtils.random(20);
		ObjectId artilceIdToUpdate = new ObjectId(listOfArticleId.get(0));
		articleService.findAndUpdateArticleById(artilceIdToUpdate, title, content);
		Article updatedArticle = articleService.findArticleById(artilceIdToUpdate);
		Assert.assertEquals(title, updatedArticle.getTitle());
	}

	@Test(expected = ArticleNotFound.class)
	public void testUpdateArticleWithInvalidId(){
		String content = RandomStringUtils.random(10);
		String title = RandomStringUtils.random(20);
		articleService.findAndUpdateArticleById(ObjectId.get(), title, content);
	}

	@Test
	public void testReloadCategoryOfArticle(){
		Article article = articleService.findArticleById(new ObjectId(listOfArticleId.get(0)));
		Category category = Category.builder()
				.categoryname(RandomStringUtils.random(10))
				.description(RandomStringUtils.random(10))
				.build();
		mongoTemplate.save(category);
		articleService.updateCategory(article, category.getId());
		articleService.updateArticleRelateToCategory(category.getId());
		Assert.assertEquals(category.getCategoryname(), article.getCategory().getCategoryname());
	}

	@Test(expected = CategoryRefError.class)
	public void testDeleteCateIdButHasRefFromArticleThrowError(){
		Article article = articleService.findArticleById(new ObjectId(listOfArticleId.get(0)));
		Category category = article.getCategory();
		articleService.getArticlesRelateToCategory(category.getId());
	}

	@Test
	public void testUpdateCategoryOfArticle(){
		Category category = Category.builder()
				.categoryname(RandomStringUtils.random(10))
				.description(RandomStringUtils.random(10))
				.build();
		mongoTemplate.save(category);
		Article article = articleService.findArticleById(new ObjectId(listOfArticleId.get(0)));
		articleService.updateCategory(article, category.getId());
		Assert.assertEquals(category.getCategoryname(), article.getCategory().getCategoryname());
	}



}
