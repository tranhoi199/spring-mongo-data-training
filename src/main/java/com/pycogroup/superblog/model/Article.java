package com.pycogroup.superblog.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "articles")
public class Article {
	@Id
	@Getter
	private String id;

	@Getter
	@Setter
	private String content;

}
