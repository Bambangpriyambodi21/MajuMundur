package com.enigma.tokopakedi.service;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.model.reponse.CustomerResponse;
import com.enigma.tokopakedi.model.request.SearchCustomerRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {


    void deleteByIdCustomer(String customerId);
    CustomerResponse readIdByCustomer(String customerId);
    Customer createNewCustomer(String CustomerId, Customer Customer);
    List<CustomerResponse> getCustomer();
    Customer createCustomer(Customer Customer);
    Page<Customer> findAllWithPagination(int page, int size);
    Page<Customer> findAllWithParamm(SearchCustomerRequest request);
    CustomerResponse updateCustomer(Customer customer);
}
