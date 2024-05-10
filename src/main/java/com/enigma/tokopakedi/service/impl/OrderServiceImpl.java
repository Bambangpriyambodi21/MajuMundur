package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.entity.Transaction;
import com.enigma.tokopakedi.entity.TransactionDetail;
import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.model.reponse.OrderDetailResponse;
import com.enigma.tokopakedi.model.reponse.OrderResponse;
import com.enigma.tokopakedi.model.request.OrderDetailRequest;
import com.enigma.tokopakedi.model.request.OrderRequest;
import com.enigma.tokopakedi.model.request.SearchOrderRequest;
import com.enigma.tokopakedi.repository.OrderRepository;
import com.enigma.tokopakedi.repository.ProductRepository;
import com.enigma.tokopakedi.service.CustomerService;
import com.enigma.tokopakedi.service.OrderDetailService;
import com.enigma.tokopakedi.service.OrderService;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailService orderDetailService;
    private final CustomerService customerService;
    private final ProductService productService;
    private final ProductRepository productRepository;



    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderResponse createTransactions(OrderRequest request) {
//        Customer customer = customerService.readIdByCustomer(request.getCustomerId());
        Customer customer1 = Customer.builder()
                .id(request.getCustomerId())
                .build();

        Transaction transaction = Transaction.builder()
                .customer(customer1)
                .date(new Date())
                .build();
        orderRepository.saveAndFlush(transaction);

        List<OrderDetailResponse> orderDetailsa = new ArrayList<>();

        for (OrderDetailRequest orderDetailRequest : request.getOrderDetails()) {

            Product product = productService.readIdProduct(orderDetailRequest.getProductId());

            TransactionDetail transactionDetail = TransactionDetail.builder()
                    .transaction(transaction)
                    .product(product)
                    .productPrice(product.getPrice())
                    .quantity(orderDetailRequest.getQuantity())
                    .build();

            if (product.getStock() - transactionDetail.getQuantity() < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "quantity exceed");
            }

            product.setStock(product.getStock() - transactionDetail.getQuantity());
            productService.update(product);

            orderDetailService.createOrUpdate(transactionDetail);
            OrderDetailResponse orderDetailResponse = OrderDetailResponse.builder()
                    .id(transactionDetail.getId())
                    .orderId(transaction.getId())
                    .product(product)
                    .productPrice(product.getPrice())
                    .quantity(orderDetailRequest.getQuantity())
                    .build();
            orderDetailsa.add(orderDetailResponse);
        }

//        order.setOrderDetails(orderDetails);
        OrderResponse orderResponse = OrderResponse.builder()
                .id(transaction.getId())
                .customerId(transaction.getCustomer().getId())
                .transDate(transaction.getDate())
                .orderDetails(orderDetailsa)
                .build();

        return orderResponse;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Transaction createTransaction(OrderRequest request) {
//        Customer customer = customerService.readIdByCustomer(request.getCustomerId());
//        Order order = Order.builder()
//                .customer(customer)
//                .date(new Date())
//                .build();
//        orderRepository.saveAndFlush(order);
//
//        List<OrderDetail> orderDetails = new ArrayList<>();
//
//        for (OrderDetailRequest orderDetailRequest : request.getOrderDetails()){
//            Product product = productService.readIdByProduct(orderDetailRequest.getProductId());
//
//            OrderDetail orderDetail = OrderDetail.builder()
//                    .order(order)
//                    .product(product)
//                    .productPrice(product.getPrice())
//                    .quantity(orderDetailRequest.getQuantity())
//                    .build();
//
//            if (product.getStock()-orderDetail.getQuantity()<0){
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "quantity exceed");
//            }
//
//            product.setStock(product.getStock()-orderDetailRequest.getQuantity());
//            productService.update(product);
//
//            orderDetails.add(orderDetail);
//        }
//
//        order.setOrderDetails(orderDetails);
//
//        return order;
        return null;
    }

    @Override
    public OrderResponse getById(String id) {
        Transaction transaction = orderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"order not found"));
        OrderResponse orderResponse = OrderResponse.builder()
                .id(transaction.getId())
                .customerId(transaction.getCustomer().getId())
                .transDate(transaction.getDate())
                .orderDetails(transaction)
                .build();
        return orderResponse;
    }

    @Override
    public Page<Transaction> getAll(SearchOrderRequest orderRequest) {
        if (orderRequest.getPage()<=0)orderRequest.setPage(1);
        Pageable pageable = PageRequest.of(orderRequest.getPage(), orderRequest.getSize());

        return orderRepository.findAll(pageable);
    }
}
