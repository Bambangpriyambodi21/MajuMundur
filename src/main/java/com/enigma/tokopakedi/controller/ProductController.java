package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.model.reponse.PagingResponse;
import com.enigma.tokopakedi.model.reponse.ProductResponse;
import com.enigma.tokopakedi.model.request.SearchProductRequest;
import com.enigma.tokopakedi.model.reponse.WebResponse;
import com.enigma.tokopakedi.repository.ProductRepository;
import com.enigma.tokopakedi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    @PostMapping(path = "/product")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'MERCHANT')")
    public ResponseEntity<?> createProduct(@RequestBody Product product){
        ProductResponse newProduct = productService.createProduct(product);
        WebResponse<ProductResponse> response = WebResponse.<ProductResponse>builder()
                .message("Succesfully create product")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(newProduct)
                .build();
        return ResponseEntity.ok(response);

    }

    @GetMapping(path = "/product")
    public ResponseEntity<?> getProduct(){
        List<ProductResponse> products = productService.getProduct();
        WebResponse<List<ProductResponse>> response = WebResponse.<List<ProductResponse>>builder()
                .message("Succesfully get all product")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(products)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/product/{productId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'MERCHANT')")
    public ResponseEntity<?> createNewProduct(@PathVariable String productId, @RequestBody Product product){
        ProductResponse product1 =productService.createNewProduct(productId, product);
        WebResponse<ProductResponse> response = WebResponse.<ProductResponse>builder()
                .message("Succesfully get all product")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(product1)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/product/{productId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'MERCHANT')")
    public ResponseEntity<?> delete(@PathVariable String productId){
        productService.deleteByIdProduct(productId);
        WebResponse<String> response = WebResponse.<String>builder()
                .message("Succesfully delete product")
                .status(HttpStatus.OK.getReasonPhrase())
                .data("ok")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/products")
    public Product updateCustomer(@RequestBody Product product) {
        Optional<Product> optionalProduct = productRepository.findById(product.getId());
        if (optionalProduct.isEmpty()) throw new RuntimeException("customer not found");
        Product updatedCustomer = productRepository.save(product);
        return updatedCustomer;
    }

}
