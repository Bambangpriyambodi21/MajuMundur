package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.Transaction;
import com.enigma.tokopakedi.model.reponse.OrderResponse;
import com.enigma.tokopakedi.model.reponse.PagingResponse;
import com.enigma.tokopakedi.model.reponse.WebResponse;
import com.enigma.tokopakedi.model.request.OrderRequest;
import com.enigma.tokopakedi.model.request.SearchOrderRequest;
import com.enigma.tokopakedi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createNewTransactions(@RequestBody OrderRequest request){

        OrderResponse transaction = orderService.createTransactions(request);

        WebResponse<OrderResponse> response = WebResponse.<OrderResponse>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Succesfully create new transaction")
                .data(transaction)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        OrderResponse order = orderService.getById(id);

        WebResponse<OrderResponse> response = WebResponse.<OrderResponse>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Successfully get all transaction")
                .data(order)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<?> getAll(@RequestParam(required = false, defaultValue = "1") Integer page,
                              @RequestParam(required = false, defaultValue = "1") Integer size){
        SearchOrderRequest orderRequest = SearchOrderRequest.builder()
                .page(page)
                .size(size)
                .build();
        Page<Transaction> order = orderService.getAll(orderRequest);
        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPage(order.getTotalPages())
                .page(orderRequest.getPage())
                .size(orderRequest.getSize())
                .totalElements(order.getTotalElements())
                .build();

//        OrderResponse orderResponse = OrderResponse.builder()
//                .id(String.valueOf(order.getContent().get()))
//                .customerId(order.getCustomer().getId())
//                .transDate(order.getDate())
//                .orderDetails(orderDetailsa)
//                .build();

        WebResponse<List<Transaction>> response = WebResponse.<List<Transaction>>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Successfully get all transaction")
                .data(order.getContent())
                .paging(pagingResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
