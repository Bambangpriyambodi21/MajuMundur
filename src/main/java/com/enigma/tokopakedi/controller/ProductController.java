package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.model.reponse.PagingResponse;
import com.enigma.tokopakedi.model.reponse.ProductResponse;
import com.enigma.tokopakedi.model.request.SearchProductRequest;
import com.enigma.tokopakedi.model.reponse.WebResponse;
import com.enigma.tokopakedi.repository.ProductRepository;
import com.enigma.tokopakedi.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository, ProductService productService1) {
        this.productService = productService1;
        this.productRepository = productRepository;
    }

    @PostMapping(path = "/product")
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
    public ResponseEntity<?> createNewProduct(@PathVariable String productId, @RequestBody Product product){
        ProductResponse product1 =productService.createNewProduct(productId, product);
        WebResponse<ProductResponse> response = WebResponse.<ProductResponse>builder()
                .message("Succesfully get all product")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(product1)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/products/bulk")
    public ResponseEntity<?> createBulkProducts(@RequestBody List<Product> products){
        List<Product> product =productService.createBulkProducts(products);
        WebResponse<List<Product>> response = WebResponse.<List<Product>>builder()
                .message("Succesfully get all bulk product")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(product)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/product/{productId}")
    public ResponseEntity<?> readIdProduct(@PathVariable String productId){
        ProductResponse product =productService.readIdByProduct(productId);
        WebResponse<ProductResponse> response = WebResponse.<ProductResponse>builder()
                .message("Succesfully get all product")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(product)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/product/{productId}")
    public ResponseEntity<?> deleteIdProduct(@PathVariable String productId){
        productService.deleteByIdProduct(productId);
        WebResponse<String> response = WebResponse.<String>builder()
                .message("Succesfully delete product")
                .status(HttpStatus.OK.getReasonPhrase())
                .data("ok")
                .build();
        return ResponseEntity.ok(response);
    }

    //SOAL 1
    @GetMapping("/entities")
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long minPrice,
            @RequestParam(required = false) Long maxPrice){

        SearchProductRequest request = SearchProductRequest.builder()
                .page(page)
                .size(size)
                .name(name)
                .minPrice(minPrice)
                .maxprice(maxPrice)
                .build();
        Page<Product> products =productService.getAll(request);

        PagingResponse pagingResponsee = PagingResponse.<Product>builder()
                .page(page)
                .size(size)
                .totalPage(products.getTotalPages())
                .totalElements(products.getTotalElements())
                .build();

        WebResponse<List<Product>> response = WebResponse.<List<Product>>builder()
                .message("Succesfully get all product")
                .status(HttpStatus.OK.getReasonPhrase())
                .paging(pagingResponsee)
                .data(products.getContent())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/products")
    public ResponseEntity<List<Product>> findAllWithParam(
            @RequestParam String name,
            @RequestParam int price){
        List<Product> products =productService.findAllWithParam(name, price);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }


    //trainer
    @PutMapping(path = "/products")
    public Product updateCustomer(@RequestBody Product product) {
        Optional<Product> optionalProduct = productRepository.findById(product.getId());
        if (optionalProduct.isEmpty()) throw new RuntimeException("customer not found");
        Product updatedCustomer = productRepository.save(product);
        return updatedCustomer;
    }

    // Delete By Id
    @DeleteMapping(path = "/products")
    public String deleteCustomerById(@PathVariable String id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) throw new RuntimeException("customer not found");
        productRepository.delete(optionalProduct.get());
        return "OK";
    }

}
