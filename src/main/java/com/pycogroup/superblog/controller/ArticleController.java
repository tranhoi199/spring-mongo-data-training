package com.pycogroup.superblog.controller;

import com.pycogroup.superblog.api.ArticlesApi;
import com.pycogroup.superblog.api.model.*;
import com.pycogroup.superblog.model.Article;
import com.pycogroup.superblog.model.Category;
import com.pycogroup.superblog.model.User;
import com.pycogroup.superblog.service.ArticleService;
import com.pycogroup.superblog.service.CategoryService;
import com.pycogroup.superblog.service.UserService;
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
		Article existArticle = articleService.findAndUpdateArticleById(new ObjectId(articleId), updateArticleRequest.getTitle(), updateArticleRequest.getContent());
		ArticleResponseModel result = modelMapper.map(existArticle, ArticleResponseModel.class);
		return new ResponseEntity<>(result, HttpStatus.FOUND);
	}

	@Override
	public ResponseEntity<ObjectCreationSuccessResponse> createArticle(@Valid CreateArticleRequest createArticleRequest) {
		//get authorId from request
		String authorId = createArticleRequest.getAuthorId();
		String categoryId = createArticleRequest.getCategory();
//		System.out.println("authorId:"+authorId);
//		System.out.println("categoryId:"+categoryId);
		// map Article to CreateArticleRequest
		Article article = modelMapper.map(createArticleRequest, Article.class);
		Article persistArticle = articleService.createArticle(article, new ObjectId(categoryId), new ObjectId(authorId));
		ObjectCreationSuccessResponse result = new ObjectCreationSuccessResponse();
		result.setId(persistArticle.getId().toString());
		result.setResponseCode(HttpStatus.CREATED.value());
		return new ResponseEntity<>(result, HttpStatus.CREATED);

	}

	@Override
	public ResponseEntity<ObjectDeletionSuccessResponse> deleteArticle(String articleId) {
		articleService.findAndDeleteArticleById(new ObjectId(articleId));
		ObjectDeletionSuccessResponse result = new ObjectDeletionSuccessResponse();
		result.setId(articleId);
		result.setResponseCode(HttpStatus.OK.value());
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ArticleResponseModel> getArticle(String articleId) {

		Article existArticle = articleService.findArticleById(new ObjectId(articleId));
		ArticleResponseModel result = modelMapper.map(existArticle, ArticleResponseModel.class);
		return new ResponseEntity<>(result, HttpStatus.FOUND);
	}

	private ResponseEntity<ArticleListResponse> buildArticleListResponse(List<Article> articleList) {
		ArticleListResponse articleListResponse = new ArticleListResponse();

		if (articleList != null) {
			articleList.forEach(item -> articleListResponse.addArticlesItem(modelMapper.map(item, com.pycogroup.superblog.api.model.ArticleResponseModel.class)));
		}
		return new ResponseEntity(articleListResponse, HttpStatus.OK);
	}
}
