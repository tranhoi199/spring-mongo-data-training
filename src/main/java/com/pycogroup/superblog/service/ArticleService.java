package com.pycogroup.superblog.service;

import com.pycogroup.superblog.model.Article;

import java.util.List;

public interface ArticleService {
	List<Article> getAllArticles();
	Article createArticle(Article article);
}
