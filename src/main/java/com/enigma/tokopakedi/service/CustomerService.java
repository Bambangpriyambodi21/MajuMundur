package com.enigma.tokopakedi.service;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.model.reponse.CustomerResponse;
import com.enigma.tokopakedi.model.request.CustomerRequest;
import com.enigma.tokopakedi.model.request.SearchCustomerRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
    String deleteByIdCustomer(String customerId);
    CustomerResponse readIdByCustomer(String customerId);
    Customer findId(String CustomerId);
    List<CustomerResponse> getCustomer();
    Customer createCustomer(Customer Customer);
    CustomerResponse updateCustomer(CustomerRequest customer);
}
