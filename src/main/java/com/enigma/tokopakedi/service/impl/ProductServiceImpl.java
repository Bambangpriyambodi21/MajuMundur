package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.model.reponse.ProductResponse;
import com.enigma.tokopakedi.model.request.SearchProductRequest;
import com.enigma.tokopakedi.repository.ProductRepository;
import com.enigma.tokopakedi.service.ProductService;
import jakarta.persistence.criteria.Predicate;
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
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void deleteByIdProduct(String id){
        Product product = new Product();
        product.setId(id);
        productRepository.deleteById(id);
    }

    @Override
    public Product readIdProduct(String productId) {
//        Product product = new Product();
//        product.setId(productId);
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) return optionalProduct.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found");
    }


    @Override
    public ProductResponse readIdByProduct(String productId) {
//        Product product = new Product();
//        product.setId(productId);
        Optional<Product> optionalProduct = productRepository.findById(productId);

        ProductResponse productResponse = ProductResponse.builder()
                .id(optionalProduct.get().getId())
                .name(optionalProduct.get().getName())
                .stock(optionalProduct.get().getStock())
                .price(optionalProduct.get().getPrice())
                .build();

        if (optionalProduct.isPresent()) return productResponse;
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found");
    }

    @Override
    public ProductResponse createNewProduct(String productId, Product product) {
        product.setId(productId);
//        Optional<Product> optionalProduct = productRepository.findById(productId);
//        if (optionalProduct.isEmpty()) throw new ResponseStatusException(HttpStatusCode.valueOf())

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

    public Page<Product> findAllWithPagination(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    @Override
    public List<Product> findAllWithParam(String param, int price) {
        Specification<Product> productSpecification = (root, query, criteriaBuilder) -> {
            return query.where(
                    criteriaBuilder.or(
                            criteriaBuilder.equal(root.get("name"), param),
                            criteriaBuilder.equal(root.get("price"), price)
                    )
            ).getRestriction();
        };

        List<Product> products = productRepository.findAll(productSpecification);
        return products;
    }

    @Override
    public List<Product> createBulkProducts(List<Product> products) {
        return productRepository.saveAll(products);
    }

    @Override
    public Page<Product> getAll(SearchProductRequest request) {
        if (request.getPage()<=0)request.setPage(1);
        PageRequest pageable = PageRequest.of(request.getPage()-1, request.getSize());
        Specification<Product> productSpecification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getName()!= null){
                Predicate namePredicate = criteriaBuilder.like(root.get("name"), "%"+request.getName()+"%");
                predicates.add(namePredicate);
            }
            if (request.getMinPrice()!= null){
                Predicate namePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("price"), request.getMinPrice());
                predicates.add(namePredicate);
            }
            if (request.getMaxprice()!= null){
                Predicate namePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("price"), request.getMaxprice());
                predicates.add(namePredicate);
            }
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
        return productRepository.findAll(productSpecification, pageable);
    }

    @Override
    public Product update(Product product) {
        product.setId(product.getId());
        Optional<Product> optionalProduct = productRepository.findById(product.getId());
        if (optionalProduct.isEmpty()) throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        return productRepository.save(product);
    }


}
