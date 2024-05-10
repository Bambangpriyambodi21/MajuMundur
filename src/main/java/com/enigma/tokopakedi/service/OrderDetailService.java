package com.enigma.tokopakedi.service;

import com.enigma.tokopakedi.entity.TransactionDetail;

public interface OrderDetailService {

    TransactionDetail createOrUpdate(TransactionDetail transactionDetail);
}
