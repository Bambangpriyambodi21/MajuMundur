package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.model.reponse.ProductResponse;
import com.enigma.tokopakedi.model.request.SearchProductRequest;
import com.enigma.tokopakedi.repository.ProductRepository;
import com.enigma.tokopakedi.service.ProductService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public void deleteByIdProduct(String id){
        Product product = new Product();
        product.setId(id);
        productRepository.deleteById(id);
    }

    @Override
    public Product readIdProduct(String productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) return optionalProduct.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found");
    }

    @Override
    public List<ProductResponse> getProduct() {
        List<Product> product = productRepository.findAll();
        List<ProductResponse> productResponses = new ArrayList<>();

        for (int i=0; i<product.size(); i++) {
            ProductResponse productResponse = ProductResponse.builder()
                    .id(product.get(i).getId())
                    .name(product.get(i).getName())
                    .stock(product.get(i).getStock())
                    .price(product.get(i).getPrice())
                    .build();
            productResponses.add(productResponse);

        }
            return productResponses;
    }

    @Override
    public ProductResponse createProduct(Product product) {
        Product products = productRepository.save(product);

            ProductResponse productResponse = ProductResponse.builder()
                    .id(products.getId())
                    .name(products.getName())
                    .stock(products.getStock())
                    .price(products.getPrice())
                    .build();

        return productResponse;
    }

    @Override
    public Product update(Product product) {
        product.setId(product.getId());
        Optional<Product> optionalProduct = productRepository.findById(product.getId());
        if (optionalProduct.isEmpty()) throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        return productRepository.save(product);
    }


}
