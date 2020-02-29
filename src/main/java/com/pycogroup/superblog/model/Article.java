package com.pycogroup.superblog.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "articles")
public class Article {
	@Id
	@Getter
	private ObjectId id;

	@Getter
	@Setter
	private String content;

	@Getter
	@Setter
	@DBRef
	private User author;

}
