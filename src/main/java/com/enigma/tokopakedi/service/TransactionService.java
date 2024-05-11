package com.enigma.tokopakedi.service;

import com.enigma.tokopakedi.entity.Transaction;
import com.enigma.tokopakedi.model.request.TransactionRequest;
import com.enigma.tokopakedi.model.reponse.TransactionResponse;
import com.enigma.tokopakedi.model.request.SearchTransactionRequest;

import java.util.List;

public interface TransactionService {
    TransactionResponse createTransactions(TransactionRequest request);
    TransactionResponse getById(String id);
    List<TransactionResponse> getAll(SearchTransactionRequest orderRequest);
    String delete(String id);
    TransactionResponse update(TransactionRequest request);
}
