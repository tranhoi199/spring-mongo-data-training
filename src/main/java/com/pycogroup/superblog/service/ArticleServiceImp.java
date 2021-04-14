package com.pycogroup.superblog.service;

import com.pycogroup.superblog.exception.ArticleNotFound;
import com.pycogroup.superblog.exception.CategoryRefError;
import com.pycogroup.superblog.model.Article;
import com.pycogroup.superblog.model.Category;
import com.pycogroup.superblog.model.QArticle;
import com.pycogroup.superblog.model.User;
import com.pycogroup.superblog.repository.ArticleRepository;
import com.pycogroup.superblog.repository.CategoryRepository;
import com.pycogroup.superblog.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImp implements ArticleService {
	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private UserService userService;

	@Override
	public List<Article> getAllArticles() {
		return articleRepository.findAll();
	}



	@Override
	public Article addUserToArticle(User user, Article article){
		article.setAuthor(user);
		articleRepository.save(article);
		return article;
	}

	@Override
	public void findAndDeleteArticleById(ObjectId articleId) {
		Article article = articleRepository.findArticleById(articleId);
		if(article == null){
			throw new ArticleNotFound(articleId.toString());
		}
		articleRepository.delete(article);
	}

	@Override
	public Article findArticleById(ObjectId articleId) {
		Article article = articleRepository.findArticleById(articleId);
		if(article == null){
			throw new ArticleNotFound(articleId.toString());
		}
		return article;
	}

	@Override
	public Article createArticle(Article article, ObjectId categoryId, ObjectId authorId) {
		User author = userService.findUserById(authorId);
		Category category = categoryService.findCategoryById(categoryId);
		article.setCategory(category);
		article.setAuthor(author);
		articleRepository.save(article);
		return article;
	}

	@Override
	public Article updateCategory(Article article, ObjectId cateId){
		Category category = categoryService.findCategoryById(cateId);
		article.setCategory(category);
		articleRepository.save(article);
		return article;
	}

	@Override
	public Article findAndUpdateArticleById(ObjectId articleId, String title, String content) {
		Article article = articleRepository.findArticleById(articleId);
		if(article == null){
			throw new ArticleNotFound(articleId.toString());
		}
		article.setContent(content);
		article.setTitle(title);
		articleRepository.save(article);
		return article;
	}

	@Override
	public Boolean getArticlesRelateToCategory(ObjectId cateId) {
		QArticle qArticle = QArticle.article;
		Iterable<Article> articles = articleRepository.findAll(qArticle.category.id.eq(cateId));
		if(articles.iterator().hasNext()){
			throw new CategoryRefError(cateId.toString());
		}
		return true;
	}

	@Override
	public void updateArticleRelateToCategory(ObjectId cateid) {
		QArticle qArticle = QArticle.article;
		Iterable<Article> articles = articleRepository.findAll(qArticle.category.id.eq(cateid));
		Category category = categoryService.findCategoryById(cateid);

		articles.forEach(item -> item.setCategory(category));
		articleRepository.saveAll(articles);
	}
}
