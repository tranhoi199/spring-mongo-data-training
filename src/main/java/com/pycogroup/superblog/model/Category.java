package com.pycogroup.superblog.model;

import com.querydsl.core.annotations.QueryEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "categories")
@QueryEntity
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @Getter
    private ObjectId id;

    @Getter
    @Setter
    private String categoryname;

    @Getter
    @Setter
    private String description;
}
