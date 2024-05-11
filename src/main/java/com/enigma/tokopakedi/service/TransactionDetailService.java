package com.enigma.tokopakedi.service;

import com.enigma.tokopakedi.entity.TransactionDetail;

public interface TransactionDetailService {

    TransactionDetail createOrUpdate(TransactionDetail transactionDetail);
    String delete(String id);
}
