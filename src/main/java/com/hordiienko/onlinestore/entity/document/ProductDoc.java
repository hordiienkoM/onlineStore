package com.hordiienko.onlinestore.entity.document;

import com.hordiienko.onlinestore.entity.enums.Brand;
import com.hordiienko.onlinestore.entity.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.*;


import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import java.time.LocalDateTime;

@Document(indexName = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDoc {
    @Id
    @Field(type = FieldType.Keyword)
    private String id;
    @Field(type = FieldType.Text)
    private String description;
    @Field(type = FieldType.Double)
    @DecimalMin("0.0")
    private Double price;
    @Field(type = FieldType.Keyword)
    private Brand brand;
    @Field(type = FieldType.Keyword)
    private Category category;
}
