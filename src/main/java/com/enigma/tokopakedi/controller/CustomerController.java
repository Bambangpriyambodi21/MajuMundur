package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.model.reponse.CustomerResponse;
import com.enigma.tokopakedi.model.reponse.PagingResponse;
import com.enigma.tokopakedi.model.reponse.WebResponse;
import com.enigma.tokopakedi.model.request.SearchCustomerRequest;
import com.enigma.tokopakedi.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

//    @PostMapping(path = "/customer")
//    public ResponseEntity<?> createCustomer(@RequestBody Customer customer){
//        Customer customer1 = customerService.createCustomer(customer);
//        WebResponse<Customer> response = WebResponse.<Customer>builder()
//                .message("Succesfully create customer")
//                .status(HttpStatus.OK.getReasonPhrase())
//                .data(customer1)
//                .build();
//        return ResponseEntity.ok(response);
//    }

    @GetMapping(path = "/customer")
    public ResponseEntity<?> getCustomer(){
        List<CustomerResponse> customers = customerService.getCustomer();
        WebResponse<List<CustomerResponse>> response = WebResponse.<List<CustomerResponse>>builder()
                .message("Succesfully get customer")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(customers)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/customer/{customerId}")
    public ResponseEntity<?> createNewCustomer(@PathVariable String customerId, @RequestBody Customer customer){
        Customer customer1 = customerService.createNewCustomer(customerId, customer);
        WebResponse<Customer> response = WebResponse.<Customer>builder()
                .message("Succesfully update customer")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(customer1)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/customer/{customerId}")
    public ResponseEntity<?> readIdCustomer(@PathVariable String customerId){
        CustomerResponse customer =customerService.readIdByCustomer(customerId);
        WebResponse<CustomerResponse> response = WebResponse.<CustomerResponse>builder()
                .message("Succesfully read by id customer")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(customer)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/customer/{customerId}")
    public ResponseEntity<?> deleteIdCustomer(@PathVariable String customerId){
        customerService.deleteByIdCustomer(customerId);
        WebResponse<String> response = WebResponse.<String>builder()
                .message("Succesfully delete customer")
                .status(HttpStatus.OK.getReasonPhrase())
                .data("ok")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customers")
    public Page<?> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size){
        if (page<=0) page =1;
        Page<Customer> customers =customerService.findAllWithPagination(page-1, size);
        return customerService.findAllWithPagination(page-1, size);
    }

    @GetMapping(path = "/customersa")
    public ResponseEntity<?> findAllWithParamm(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size){
        SearchCustomerRequest requesta = SearchCustomerRequest.builder()
                .name(name)
                .phone(phone)
                .page(page)
                .size(size)
                .build();

        Page<Customer> customers =customerService.findAllWithParamm(requesta);
        PagingResponse pagingResponsee = PagingResponse.builder()
                .page(page)
                .size(size)
                .totalPage(customers.getTotalPages())
                .totalElements(customers.getTotalElements())
                .build();
        WebResponse<List<Customer>> response = WebResponse.<List<Customer>>builder()
                .message("Succesfully get all Customer")
                .status(HttpStatus.OK.getReasonPhrase())
                .paging(pagingResponsee)
                .data(customers.getContent())
                .build();
        return ResponseEntity.ok(response);
    }

    //TRAINER
    // Update
    @PutMapping(path = "/customers")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {
        CustomerResponse customer1 = customerService.updateCustomer(customer);
        WebResponse<CustomerResponse> response = WebResponse.<CustomerResponse>builder()
                .message("Succesfully edit Customer")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(customer1)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Delete By Id
    @DeleteMapping(path = "/customers/{id}")
    public String deleteCustomerById(@PathVariable String id) {
//        Optional<Customer> optionalCustomer = customerRepository.findById(id);
//        if (optionalCustomer.isEmpty()) throw new RuntimeException("customer not found");
//        customerRepository.delete(optionalCustomer.get());
        return "OK";
    }

}
