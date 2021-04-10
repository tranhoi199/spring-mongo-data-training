package com.pycogroup.superblog.service;

import com.pycogroup.superblog.model.Article;
import com.pycogroup.superblog.model.Category;
import com.pycogroup.superblog.model.User;
import org.bson.types.ObjectId;

import java.util.List;

public interface ArticleService {
	List<Article> getAllArticles();
	Article findArticleById(ObjectId articleId);
	Article createArticle(Article article);
	Article addUserToArticle(User user, Article articleId);
	Article addCategoryToArticle(Article article, Category category);
	Article findAndUpdateArticleById(ObjectId articleId, String title, String content);
	void findAndDeleteArticleById(ObjectId articleId);
}
