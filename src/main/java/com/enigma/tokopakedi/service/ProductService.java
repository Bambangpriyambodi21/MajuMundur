package com.enigma.tokopakedi.service;

import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.model.reponse.ProductResponse;
import com.enigma.tokopakedi.model.request.SearchProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    void deleteByIdProduct(String id);
    List<ProductResponse> getProduct();
    ProductResponse createProduct(Product product);
    Product update(Product product);
    Product readIdProduct(String productId);

}
