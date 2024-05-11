package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.Transaction;
import com.enigma.tokopakedi.model.reponse.TransactionResponse;
import com.enigma.tokopakedi.model.reponse.PagingResponse;
import com.enigma.tokopakedi.model.reponse.WebResponse;
import com.enigma.tokopakedi.model.request.TransactionRequest;
import com.enigma.tokopakedi.model.request.SearchTransactionRequest;
import com.enigma.tokopakedi.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> createNewTransactions(@RequestBody TransactionRequest request){

        TransactionResponse transaction = transactionService.createTransactions(request);

        WebResponse<TransactionResponse> response = WebResponse.<TransactionResponse>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Succesfully create new transaction")
                .data(transaction)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        TransactionResponse order = transactionService.getById(id);

        WebResponse<TransactionResponse> response = WebResponse.<TransactionResponse>builder()
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
        SearchTransactionRequest orderRequest = SearchTransactionRequest.builder()
                .page(page)
                .size(size)
                .build();
        List<TransactionResponse> order = transactionService.getAll(orderRequest);

        WebResponse<List<TransactionResponse>> response = WebResponse.<List<TransactionResponse>>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Successfully get all transaction")
                .data(order)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        String delete = transactionService.delete(id);

        WebResponse<String> response = WebResponse.<String>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Successfully get all transaction")
                .data(delete)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody TransactionRequest request){
        TransactionResponse update = transactionService.update(request);
        WebResponse<TransactionResponse> response = WebResponse.<TransactionResponse>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Succesfully create new transaction")
                .data(update)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
