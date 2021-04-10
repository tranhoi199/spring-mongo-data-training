package com.pycogroup.superblog.service;

import com.pycogroup.superblog.exception.ArticleNotFound;
import com.pycogroup.superblog.model.Article;
import com.pycogroup.superblog.model.Category;
import com.pycogroup.superblog.model.User;
import com.pycogroup.superblog.repository.ArticleRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImp implements ArticleService {
	@Autowired
	private ArticleRepository articleRepository;


	@Override
	public List<Article> getAllArticles() {
		return articleRepository.findAll();
	}

	@Override
	public Article createArticle(Article article) {
		return articleRepository.save(article);
	}

	@Override
	public Article addUserToArticle(User user, Article article){
		article.setAuthor(user);
		articleRepository.save(article);
		return article;
	}

	@Override
	public Article addCategoryToArticle(Article article, Category category) {
		article.setCategory(category);
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
}
