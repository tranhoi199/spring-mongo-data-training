package com.pycogroup.superblog.model;

import com.pycogroup.superblog.api.model.CategoryResponseModel;
import com.querydsl.core.annotations.QueryEntity;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "categories")
@QueryEntity
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
