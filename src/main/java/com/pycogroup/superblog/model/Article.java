package com.pycogroup.superblog.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "articles")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {
	@Id
	@Getter
	private ObjectId id;

	@Getter
	@Setter
	private String title;

	@Getter
	@Setter
	private String content;

	@Getter
	@Setter
	private User author;

	@Getter
	@Setter
	private Category category;
}
