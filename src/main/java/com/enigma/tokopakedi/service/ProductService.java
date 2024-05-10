package com.enigma.tokopakedi.service;

import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.model.reponse.ProductResponse;
import com.enigma.tokopakedi.model.request.SearchProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    void deleteByIdProduct(String id);
    ProductResponse readIdByProduct(String productId);
    ProductResponse createNewProduct(String productId, Product product);
    List<ProductResponse> getProduct();
    ProductResponse createProduct(Product product);
    Page<Product> findAllWithPagination(int page, int size);
    List<Product> findAllWithParam(String name,int price);
    List<Product> createBulkProducts(List<Product> products);
    Page<Product> getAll(SearchProductRequest request);
    Product update(Product product);
    Product readIdProduct(String productId);

}
