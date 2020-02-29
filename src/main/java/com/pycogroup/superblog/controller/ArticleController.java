package com.pycogroup.superblog.controller;

import com.pycogroup.superblog.api.ArticlesApi;
import com.pycogroup.superblog.api.model.ArticleListResponse;
import com.pycogroup.superblog.api.model.CreateArticleRequest;
import com.pycogroup.superblog.api.model.ObjectCreationSuccessResponse;
import com.pycogroup.superblog.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
public class ArticleController implements ArticlesApi {
	@Autowired
	private ArticleRepository articleRepository;
	@Override
	public ResponseEntity<ArticleListResponse> getArticleList() {
		return null;
	}

	@Override
	public ResponseEntity<ObjectCreationSuccessResponse> createArticle(@Valid CreateArticleRequest createArticleRequest) {
		return null;
	}
}
