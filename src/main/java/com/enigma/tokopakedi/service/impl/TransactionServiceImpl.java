package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.entity.Transaction;
import com.enigma.tokopakedi.entity.TransactionDetail;
import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.model.reponse.TransactionDetailResponse;
import com.enigma.tokopakedi.model.reponse.TransactionResponse;
import com.enigma.tokopakedi.model.request.TransactionDetailRequest;
import com.enigma.tokopakedi.model.request.TransactionRequest;
import com.enigma.tokopakedi.model.request.SearchTransactionRequest;
import com.enigma.tokopakedi.repository.TransactionRepository;
import com.enigma.tokopakedi.repository.ProductRepository;
import com.enigma.tokopakedi.service.CustomerService;
import com.enigma.tokopakedi.service.TransactionDetailService;
import com.enigma.tokopakedi.service.TransactionService;
import com.enigma.tokopakedi.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionDetailService transactionDetailService;
    private final CustomerService customerService;
    private final ProductService productService;
    private final ProductRepository productRepository;



    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransactionResponse createTransactions(TransactionRequest request) {
//        Customer customer = customerService.readIdByCustomer(request.getCustomerId());
        Customer customer1 = Customer.builder()
                .id(request.getCustomerId())
                .build();

        Transaction transaction = Transaction.builder()
                .customer(customer1)
                .date(new Date())
                .build();
        transactionRepository.saveAndFlush(transaction);

        List<TransactionDetailResponse> orderDetailsa = new ArrayList<>();

        for (TransactionDetailRequest transactionDetailRequest : request.getOrderDetails()) {

            Product product = productService.readIdProduct(transactionDetailRequest.getProductId());

            TransactionDetail transactionDetail = TransactionDetail.builder()
                    .transaction(transaction)
                    .product(product)
                    .productPrice(product.getPrice())
                    .quantity(transactionDetailRequest.getQuantity())
                    .build();

            if (product.getStock() - transactionDetail.getQuantity() < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "quantity exceed");
            }

            product.setStock(product.getStock() - transactionDetail.getQuantity());
            productService.update(product);

            transactionDetailService.createOrUpdate(transactionDetail);
            TransactionDetailResponse transactionDetailResponse = TransactionDetailResponse.builder()
                    .id(transactionDetail.getId())
                    .orderId(transaction.getId())
                    .product(product)
                    .productPrice(product.getPrice())
                    .quantity(transactionDetailRequest.getQuantity())
                    .build();
            orderDetailsa.add(transactionDetailResponse);
        }

//        order.setOrderDetails(orderDetails);
        TransactionResponse transactionResponse = TransactionResponse.builder()
                .id(transaction.getId())
                .customerId(transaction.getCustomer().getId())
                .transDate(transaction.getDate())
                .orderDetails(orderDetailsa)
                .build();

        return transactionResponse;
    }

    @Override
    public TransactionResponse getById(String id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"order not found"));
        TransactionResponse transactionResponse = TransactionResponse.builder()
                .id(transaction.getId())
                .customerId(transaction.getCustomer().getId())
                .transDate(transaction.getDate())
                .orderDetails(transaction)
                .build();
        return transactionResponse;
    }

    @Override
    public List<TransactionResponse> getAll(SearchTransactionRequest orderRequest) {
        if (orderRequest.getPage()<=0)orderRequest.setPage(1);
        PageRequest pageable = PageRequest.of(orderRequest.getPage(), orderRequest.getSize());

        List<Transaction> all = transactionRepository.findAll();
        List<TransactionResponse> transactionResponses = all.stream()
                .map(data -> TransactionResponse.builder()
                        .id(data.getId())
                        .transDate(data.getDate())
                        .customerId(data.getCustomer().getId())
                        .orderDetails(data.getTransactionDetails())
                        .build())
                .collect(Collectors.toList());
        return transactionResponses;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String delete(String id) {
        Optional<Transaction> byId = transactionRepository.findById(id);
        for (int i=0;i<byId.get().getTransactionDetails().size();i++){
            transactionDetailService.delete(byId.get().getTransactionDetails().get(i).getId());
        }
        transactionRepository.deleteById(id);
        return "Transaction deleted";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransactionResponse update(TransactionRequest request) {
        Optional<Transaction> byId = transactionRepository.findById(request.getId());
        if (byId.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transaction not found");

        Customer customer1 = Customer.builder()
                .id(request.getCustomerId())
                .build();

        Transaction transaction = Transaction.builder()
                .id(request.getId())
                .customer(customer1)
                .date(new Date())
                .build();
        transactionRepository.saveAndFlush(transaction);

        List<TransactionDetailResponse> orderDetailsa = new ArrayList<>();

        for (int i = 0; i<request.getOrderDetails().size();i++) {

            Product product = productService.readIdProduct(request.getOrderDetails().get(i).getProductId());

            TransactionDetail transactionDetail = TransactionDetail.builder()
                    .id(request.getOrderDetails().get(i).getId())
                    .transaction(transaction)
                    .product(product)
                    .productPrice(product.getPrice())
                    .quantity(request.getOrderDetails().get(i).getQuantity())
                    .build();

            if (product.getStock() - transactionDetail.getQuantity() < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "quantity exceed");
            }

            productService.update(product);

            transactionDetailService.createOrUpdate(transactionDetail);
            TransactionDetailResponse transactionDetailResponse = TransactionDetailResponse.builder()
                    .id(transactionDetail.getId())
                    .orderId(transaction.getId())
                    .product(product)
                    .productPrice(product.getPrice())
                    .quantity(request.getOrderDetails().get(i).getQuantity())
                    .build();
            orderDetailsa.add(transactionDetailResponse);
        }

        TransactionResponse transactionResponse = TransactionResponse.builder()
                .id(transaction.getId())
                .customerId(transaction.getCustomer().getId())
                .transDate(transaction.getDate())
                .orderDetails(orderDetailsa)
                .build();

        return transactionResponse;
    }
}
