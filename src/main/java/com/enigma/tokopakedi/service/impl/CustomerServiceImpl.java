package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.entity.UserCredential;
import com.enigma.tokopakedi.model.reponse.CustomerResponse;
import com.enigma.tokopakedi.model.reponse.RoleResponse;
import com.enigma.tokopakedi.model.reponse.UserResponse;
import com.enigma.tokopakedi.model.request.CustomerRequest;
import com.enigma.tokopakedi.model.request.SearchCustomerRequest;
import com.enigma.tokopakedi.model.reponse.UserCredentialResponse;
import com.enigma.tokopakedi.repository.CustomerRepository;
import com.enigma.tokopakedi.repository.UserCredentialRepository;
import com.enigma.tokopakedi.service.CustomerService;
import com.enigma.tokopakedi.service.UserService;
import jakarta.persistence.RollbackException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserService userService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public CustomerResponse readIdByCustomer(String customerId) {
        Customer customers = new Customer();
        customers.setId(customerId);
        Customer customer = customerRepository.findById(customerId).orElseThrow();

        List<UserCredentialResponse> userCredentialResponses = new ArrayList<>();

        UserCredentialResponse credentialResponse = UserCredentialResponse.builder()
                .id(customer.getUserCredential().getId())
                .email(customer.getUserCredential().getEmail())
                .roles(customer.getUserCredential().getRoles())
                .build();
        userCredentialResponses.add(credentialResponse);

        CustomerResponse customerResponse = CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .address(customer.getAddress())
                .phone(customer.getPhone())
                .userCredential(userCredentialResponses)
                .build();
        return customerResponse;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Customer createNewCustomer(String customerId, Customer customer) {
        customer.setId(customerId);
        return customerRepository.save(customer);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<CustomerResponse> getCustomer() {
        List<Customer> customer =customerRepository.findAll();

        List<UserCredentialResponse> userCredentialResponses = new ArrayList<>();
        List<CustomerResponse> customerResponses = new ArrayList<>();

        for (int i=0; i<customer.size(); i++){
        UserCredentialResponse credentialResponse = UserCredentialResponse.builder()
                .id(customer.get(i).getUserCredential().getId())
                .email(customer.get(i).getUserCredential().getEmail())
                .roles(customer.get(i).getUserCredential().getRoles())
                .build();
        userCredentialResponses.add(credentialResponse);

            log.info(String.valueOf(userCredentialResponses.get(i)));
        CustomerResponse customerResponse = CustomerResponse.builder()
                .id(customer.get(i).getId())
                .name(customer.get(i).getName())
                .address(customer.get(i).getAddress())
                .phone(customer.get(i).getPhone())
                .userCredential(userCredentialResponses.get(i))
                .build();
        customerResponses.add(customerResponse);

//            log.info(String.valueOf(userCredentialResponses.get(i).getEmail()));
        }
        log.info(customerResponses.toString());
        return customerResponses;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deleteByIdCustomer(String customerId){
        Optional<Customer> byId = customerRepository.findById(customerId);
        if (byId.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        userService.delete(byId.get().getUserCredential().getId());
        customerRepository.deleteById(customerId);
        return "Data customer deleted";
    }

    public Page<Customer> findAllWithPagination(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return customerRepository.findAll(pageable);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Page<Customer> findAllWithParamm(SearchCustomerRequest request) {
        if (request.getPage()<=0)request.setPage(1);
        PageRequest pageablee = PageRequest.of(request.getPage()-1, request.getSize());
        Specification<Customer> productSpecificationn = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getName()!= null || request.getPhone()!= null){
                Predicate namePredicate = criteriaBuilder.or(
                        criteriaBuilder.like(root.get("name"), "%"+request.getName()+"%"),
                        criteriaBuilder.like(root.get("phone"), "%"+request.getPhone()+"%")
                );
                predicates.add(namePredicate);
            }
//            if (request.getPhone()!= null){
//                Predicate namePredicate = criteriaBuilder.like(root.get("phone"), "%"+request.getPhone()+"%");
//                predicates.add(namePredicate);
//            }
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        return customerRepository.findAll(productSpecificationn, pageablee);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CustomerResponse updateCustomer(CustomerRequest customer) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customer.getId());
        if (optionalCustomer.isEmpty()) throw new RuntimeException("customer not found");

        List<UserCredentialResponse> userCredentialResponses = new ArrayList<>();

        UserCredential currentUserCredential = (UserCredential) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserCredential userCredential = userService.loadByUserId(customer.getUserCredential());
        if (!currentUserCredential.getId().equals(userCredential.getId())) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "forbidden");
        Customer customer1 = Customer.builder()
                .id(customer.getId())
                .name(customer.getName())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .poin(customer.getPoin())
                .userCredential(userCredential)
                .build();

        Customer updatedCustomer = customerRepository.save(customer1);
        UserCredentialResponse credentialResponse = UserCredentialResponse.builder()
                .id(updatedCustomer.getUserCredential().getId())
                .email(updatedCustomer.getUserCredential().getEmail())
                .roles(updatedCustomer.getUserCredential().getRoles())
                .build();
        userCredentialResponses.add(credentialResponse);

        CustomerResponse customerResponse = CustomerResponse.builder()
                .id(updatedCustomer.getId())
                .name(updatedCustomer.getName())
                .address(updatedCustomer.getAddress())
                .phone(updatedCustomer.getPhone())
                .poin(updatedCustomer.getPoin())
                .userCredential(userCredentialResponses)
                .build();
        return customerResponse;
    }


}
