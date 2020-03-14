package com.pycogroup.superblog.model;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "admin_users")
@Builder
@QueryEntity
public class User {
	@Id
	@Getter
	private ObjectId id;

	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private String email;

	@Getter
	@Setter
	private Address address;

	@Getter
	@Setter
	private Boolean enabled;
}
