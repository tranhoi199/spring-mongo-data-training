package com.pycogroup.superblog.controller;

import com.pycogroup.superblog.api.ArticlesApi;
import com.pycogroup.superblog.api.model.*;
import com.pycogroup.superblog.model.Article;
import com.pycogroup.superblog.model.Category;
import com.pycogroup.superblog.model.User;
import com.pycogroup.superblog.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class ArticleController implements ArticlesApi {
	@Autowired
	private ArticleService articleService;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ResponseEntity<ArticleListResponse> getArticleList() {
		List<Article> articleList = articleService.getAllArticles();
		return buildArticleListResponse(articleList);
	}

	@Override
	public ResponseEntity<ArticleResponseModel> updateArticle(String articleId, @Valid UpdateArticleRequest updateArticleRequest) {
		return null;
	}

	@Override
	public ResponseEntity<ObjectCreationSuccessResponse> createArticle(@Valid CreateArticleRequest createArticleRequest) {
//		//get authorId from request
//		String authorId = createArticleRequest.getAuthorId();
//
//		String categoryId = createArticleRequest.getCategory();
//		// map Article to CreateArticleRequest
//		Article article = modelMapper.map(createArticleRequest, Article.class);
//		Article persistArticle = articleService.createArticle(article);
//
//		//embedded user to a article
//		User persistUser = userService.findById(authorId);
//		Article updateArticle = articleService.addUserToArticle(persistUser, persistArticle);
//
//		// add categories to a article
//		Category category = categoryService.findCategoryById(new ObjectId(categoryId));
//		articleService.addCategoryToArticle(article, category);
//
//		ObjectCreationSuccessResponse result = new ObjectCreationSuccessResponse();
//		result.setId(persistArticle.getId().toString());
//		result.setResponseCode(HttpStatus.CREATED.value());
//		return new ResponseEntity<>(result, HttpStatus.CREATED);
		return null;
	}

	@Override
	public ResponseEntity<ObjectDeletionSuccessResponse> deleteArticle(String articleId) {
		return null;
	}

	@Override
	public ResponseEntity<ArticleResponseModel> getArticle(String articleId) {
		return null;
	}

	private ResponseEntity<ArticleListResponse> buildArticleListResponse(List<Article> articleList) {
		ArticleListResponse articleListResponse = new ArticleListResponse();

		if (articleList != null) {
			articleList.forEach(item -> articleListResponse.addArticlesItem(modelMapper.map(item, com.pycogroup.superblog.api.model.ArticleResponseModel.class)));
		}
		return new ResponseEntity(articleListResponse, HttpStatus.OK);
	}
}
