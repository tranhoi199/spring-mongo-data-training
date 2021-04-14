package com.pycogroup.superblog.service;

import com.pycogroup.superblog.model.Article;
import com.pycogroup.superblog.model.Category;
import com.pycogroup.superblog.model.User;
import org.bson.types.ObjectId;

import java.util.List;

public interface ArticleService {
	List<Article> getAllArticles();
	Article findArticleById(ObjectId articleId);
	Article createArticle(Article article, ObjectId categoryId, ObjectId authorId);
	Article addUserToArticle(User user, Article articleId);
	Article updateCategory(Article article, ObjectId cateId);
	Article findAndUpdateArticleById(ObjectId articleId, String title, String content);
	Boolean getArticlesRelateToCategory(ObjectId cateId);
	void updateArticleRelateToCategory(ObjectId cateid);
	void findAndDeleteArticleById(ObjectId articleId);
}
