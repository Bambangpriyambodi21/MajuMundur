package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.model.reponse.CustomerResponse;
import com.enigma.tokopakedi.model.reponse.PagingResponse;
import com.enigma.tokopakedi.model.reponse.WebResponse;
import com.enigma.tokopakedi.model.request.CustomerRequest;
import com.enigma.tokopakedi.model.request.SearchCustomerRequest;
import com.enigma.tokopakedi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<?> getCustomer(){
        List<CustomerResponse> customers = customerService.getCustomer();
        WebResponse<List<CustomerResponse>> response = WebResponse.<List<CustomerResponse>>builder()
                .message("Succesfully get customer")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(customers)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{customerId}")
    public ResponseEntity<?> deleteIdCustomer(@PathVariable String customerId){
        customerService.deleteByIdCustomer(customerId);
        WebResponse<String> response = WebResponse.<String>builder()
                .message("Succesfully delete customer")
                .status(HttpStatus.OK.getReasonPhrase())
                .data("ok")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerRequest customer) {
        CustomerResponse customer1 = customerService.updateCustomer(customer);
        WebResponse<CustomerResponse> response = WebResponse.<CustomerResponse>builder()
                .message("Succesfully edit Customer")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(customer1)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
