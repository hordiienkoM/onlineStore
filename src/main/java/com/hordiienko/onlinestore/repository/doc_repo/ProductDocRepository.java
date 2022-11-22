package com.hordiienko.onlinestore.repository.doc_repo;

import com.hordiienko.onlinestore.entity.document.ProductDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository(value = "productDocRepository")
public interface ProductDocRepository extends ElasticsearchRepository<ProductDoc, String> {
    Set<ProductDoc> findAllBy();
}
