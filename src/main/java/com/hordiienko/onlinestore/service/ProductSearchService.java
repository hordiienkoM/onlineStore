package com.hordiienko.onlinestore.service;


import com.hordiienko.onlinestore.entity.Product;
import com.hordiienko.onlinestore.entity.document.ProductDoc;
import com.hordiienko.onlinestore.entity.enums.Brand;
import com.hordiienko.onlinestore.entity.enums.Category;
import com.hordiienko.onlinestore.exception.ProductNotFoundException;
import com.hordiienko.onlinestore.mapper.ProductDocMapper;
import com.hordiienko.onlinestore.repository.ProductRepository;
import com.hordiienko.onlinestore.repository.doc_repo.ProductDocRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexedObjectInformation;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@Service
@Slf4j
public class ProductSearchService {
    @Autowired
    private ProductDocMapper productDocMapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductDocRepository productDocRepository;
    private static final String PRODUCT_INDEX = "product";
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public List<IndexedObjectInformation> createProductIndexBulk(Stream<Product> products) {

        List<IndexQuery> queries = products.map(product -> {
                    ProductDoc productDoc = productDocMapper.toProductDoc(product);
                    return new IndexQueryBuilder()
                            .withId(product.getId())
                            .withObject(productDoc).build();
                })
                .toList();

        return elasticsearchOperations
                .bulkIndex(queries, IndexCoordinates.of(PRODUCT_INDEX));
    }

    public void saveProductIndex(Product product) {
        ProductDoc productDoc = productDocMapper.toProductDoc(product);

        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(product.getId())
                .withObject(productDoc).build();

        elasticsearchOperations
                .index(indexQuery, IndexCoordinates.of(PRODUCT_INDEX));
    }

    public void saveProductIndexBulk(List<Product> products) {
        List<IndexQuery> queries = products.stream()
                .map(product -> {
                    ProductDoc productDoc = productDocMapper.toProductDoc(product);
                    return new IndexQueryBuilder()
                            .withId(product.getId())
                            .withObject(productDoc).build();
                })
                .toList();

        elasticsearchOperations
                .bulkIndex(queries, IndexCoordinates.of(PRODUCT_INDEX));
    }

    @Transactional
    public void createAllProductIndices() {
        Stream<Product> products = productRepository.streamAllBy();
        List<IndexQuery> queries = products.map(product -> {
                    ProductDoc productDoc = productDocMapper.toProductDoc(product);
                    return new IndexQueryBuilder()
                            .withId(product.getId())
                            .withObject(productDoc).build();
                })
                .toList();

        elasticsearchOperations.bulkIndex(queries, IndexCoordinates.of(PRODUCT_INDEX));
    }

    public boolean existsByDescription(String description, Locale locale) {
        QueryBuilder queryBuilder =
                matchQuery("description", description);

        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .build();

        SearchHits<Product> productHits =
                elasticsearchOperations
                        .search(searchQuery,
                                Product.class,
                                IndexCoordinates.of(PRODUCT_INDEX));

        return productHits.hasSearchHits();
    }

    @Transactional
    public Set<ProductDoc> getAllProducts() {
        return productDocRepository.findAllBy();
    }

    public void deleteAll() {
        productDocRepository.deleteAll();
    }

    public void deleteById(String productId) {
        productDocRepository.deleteById(productId);
    }

    public ProductDoc getHasMaxPrice() {
        Query query = new NativeSearchQueryBuilder()
                .withSort(Sort.by("price").descending())
                .withMaxResults(1)
                .build();

        SearchHits<ProductDoc> products = elasticsearchOperations.search(query, ProductDoc.class);
        return products
                .getSearchHits()
                .get(0)
                .getContent();
    }

    public double getAveragePrice(Locale locale) {
        SearchRequest searchRequest = new SearchRequest("product");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        AvgAggregationBuilder avgAggregationBuilder = AggregationBuilders.avg("avg_price").field("price");
        searchSourceBuilder.size(0).aggregation(avgAggregationBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response;
        try {
            response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new ProductNotFoundException(locale);
        }

        Avg agg = response.getAggregations().get("avg_price");

        return Math.round(
                agg.getValue() * 100
        ) / 100.0;
    }

    public Set<ProductDoc> getIds20HasBrand(String brandName, Locale locale) {

        Query query = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("brand", brandName))
                .withMaxResults(20)
                .build();

        return elasticsearchOperations.search(query, ProductDoc.class).stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toSet());
    }

    public Set<ProductDoc> get20MinHasCategory(String categoryName, Locale locale) {
        Query query = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("category", categoryName))
                .withSort(Sort.by("price").ascending())
                .withMaxResults(20)
                .build();

        return elasticsearchOperations.search(query, ProductDoc.class).stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toSet());
    }


    public Double averagePriceFromCategory(String categoryName, Locale locale) {
        SearchRequest searchRequest = new SearchRequest("product");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(matchQuery("category", categoryName));
        AvgAggregationBuilder avgAggregationBuilder = AggregationBuilders.avg("avg_price").field("price");
        searchSourceBuilder.size(0).aggregation(avgAggregationBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response;
        try {
            response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new ProductNotFoundException(locale);
        }

        Avg categoryAvg = response.getAggregations().get("avg_price");

        return Math.round(
                categoryAvg.getValue() * 100
        ) / 100.0;
    }

    public Double minPriceFromCategory(String categoryName, Locale locale) {
        SearchRequest searchRequest = new SearchRequest("product");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(matchQuery("category", categoryName));
        MinAggregationBuilder minAggregationBuilder = AggregationBuilders.min("min_price").field("price");
        searchSourceBuilder.size(0).aggregation(minAggregationBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response;
        try {
            response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new ProductNotFoundException(locale);
        }

        Min categoryMin = response.getAggregations().get("min_price");

        return Math.round(
                categoryMin.getValue() * 100
        ) / 100.0;
    }

    public Double maxPriceFromBrandInCategory(String categoryName, String brandName, Locale locale) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(matchQuery("category", categoryName));
        boolQueryBuilder.must(matchQuery("brand", brandName));

        SearchRequest searchRequest = new SearchRequest("product");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        MaxAggregationBuilder maxAggregationBuilder = AggregationBuilders.max("max_price").field("price");
        searchSourceBuilder.size(0).aggregation(maxAggregationBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response;
        try {
            response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new ProductNotFoundException(locale);
        }

        Max categoryMax = response.getAggregations().get("max_price");

        return Math.round(
                categoryMax.getValue() * 100
        ) / 100.0;
    }

    private List<String> getCategoryBrandListIds(String categoryName, String brandName) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(matchQuery("category", categoryName));
        boolQueryBuilder.must(matchQuery("brand", brandName));

        Query query = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .build();

        return elasticsearchOperations.search(query, ProductDoc.class).stream()
                .map(SearchHit::getContent)
                .map(ProductDoc::getId)
                .toList();
    }

    @Transactional
    public Map<Category, Map<Brand, List<String>>> getMapStructure(Locale locale) {
        SearchRequest searchReq = new SearchRequest("product");

        TermsAggregationBuilder categoryAgg = AggregationBuilders.terms("category").field("category").size(100);
        TermsAggregationBuilder brandAgg = AggregationBuilders.terms("brand").field("brand").size(100);

        categoryAgg.subAggregation(brandAgg);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.aggregation(categoryAgg);

        searchReq.source(sourceBuilder);

        SearchResponse response;
        try {
            response = restHighLevelClient.search(searchReq, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new ProductNotFoundException(locale);
        }

        Terms categoryTerms = response.getAggregations().get("category");
        Map<Category, Map<Brand, List<String>>> result = new HashMap<>();
        for (Terms.Bucket categoryBucket : categoryTerms.getBuckets()) {
            String categoryName = categoryBucket.getKey().toString();
            Category category = Category.valueOf(categoryName);

            System.out.println(categoryName);
            Terms brandTerms = categoryBucket.getAggregations().get("brand");
            Map<Brand, List<String>> brandProducts = new HashMap<>();
            for (Terms.Bucket brandBucket : brandTerms.getBuckets()) {
                String brandName = brandBucket.getKey().toString();
                Brand brand = Brand.valueOf(brandName);

                List<String> ids = getCategoryBrandListIds(categoryName, brandName);
                brandProducts.put(brand, ids);
            }
            result.put(category, brandProducts);
        }
        return result;
    }

    public Map<Category, Map<Brand, Double>> getCategoryBrandSum(Locale locale) {
        SearchRequest searchReq = new SearchRequest("product");

        TermsAggregationBuilder categoryAgg = AggregationBuilders.terms("category").field("category").size(100);
        TermsAggregationBuilder brandAgg = AggregationBuilders.terms("brand").field("brand").size(100);

        brandAgg.subAggregation(AggregationBuilders.sum("sum").field("price"));
        categoryAgg.subAggregation(brandAgg);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.aggregation(categoryAgg);

        searchReq.source(sourceBuilder);

        SearchResponse response;
        try {
            response = restHighLevelClient.search(searchReq, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new ProductNotFoundException(locale);
        }

        Terms categoryTerms = response.getAggregations().get("category");
        Map<Category, Map<Brand, Double>> result = new HashMap<>();
        for (Terms.Bucket categoryBucket : categoryTerms.getBuckets()) {
            String categoryName = categoryBucket.getKey().toString();
            Category category = Category.valueOf(categoryName);

            Map<Brand, Double> brandSum = new HashMap<>();
            Terms brandTerms = categoryBucket.getAggregations().get("brand");
            for (Terms.Bucket brandBucket : brandTerms.getBuckets()) {
                String brandName = brandBucket.getKey().toString();
                Brand brand = Brand.valueOf(brandName);
                Sum sum = brandBucket.getAggregations().get("sum");
                Double roundSum = Math.round(sum.getValue() * 100) / 100.0;
                brandSum.put(brand, roundSum);
            }
            result.put(category, brandSum);
        }
        return result;
    }

    public Map<Category, Map<Brand, Double>> getCategoryBrandMaxPrice(Locale locale) {
        SearchRequest searchReq = new SearchRequest("product");

        TermsAggregationBuilder categoryAgg = AggregationBuilders.terms("category").field("category").size(100);
        TermsAggregationBuilder brandAgg = AggregationBuilders.terms("brand").field("brand").size(100);

        brandAgg.subAggregation(AggregationBuilders.max("max").field("price"));
        categoryAgg.subAggregation(brandAgg);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.aggregation(categoryAgg);

        searchReq.source(sourceBuilder);

        SearchResponse response;
        try {
            response = restHighLevelClient.search(searchReq, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new ProductNotFoundException(locale);
        }

        Terms categoryTerms = response.getAggregations().get("category");
        Map<Category, Map<Brand, Double>> result = new HashMap<>();
        for (Terms.Bucket categoryBucket : categoryTerms.getBuckets()) {
            String categoryName = categoryBucket.getKey().toString();
            Category category = Category.valueOf(categoryName);

            Map<Brand, Double> brandSum = new HashMap<>();
            Terms brandTerms = categoryBucket.getAggregations().get("brand");
            for (Terms.Bucket brandBucket : brandTerms.getBuckets()) {
                String brandName = brandBucket.getKey().toString();
                Brand brand = Brand.valueOf(brandName);
                Max max = brandBucket.getAggregations().get("max");
                Double roundMax = Math.round(max.getValue() * 100) / 100.0;
                brandSum.put(brand, roundMax);
            }
            result.put(category, brandSum);
        }
        return result;
    }
}
