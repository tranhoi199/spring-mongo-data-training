package com.pycogroup.superblog.controller;

import com.pycogroup.superblog.api.ArticlesApi;
import com.pycogroup.superblog.api.model.*;
import com.pycogroup.superblog.model.Article;
import com.pycogroup.superblog.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
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
	public ResponseEntity<ObjectCreationSuccessResponse> createArticle(@Valid CreateArticleRequest createArticleRequest) {
		return null;
	}

	private ResponseEntity<ArticleListResponse> buildArticleListResponse(List<Article> articleList) {
		ArticleListResponse articleListResponse = new ArticleListResponse();
		if(articleList != null) {
			articleList.forEach(item->articleListResponse.addArticlesItem(modelMapper.map(item, ArticleResponseModel.class)));
		}
		return new ResponseEntity(articleListResponse, HttpStatus.OK);
	}
}
