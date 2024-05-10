package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.entity.TransactionDetail;
import com.enigma.tokopakedi.repository.OrderDetailRepository;
import com.enigma.tokopakedi.service.OrderDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransactionDetail createOrUpdate(TransactionDetail transactionDetail) {
        return orderDetailRepository.save(transactionDetail);
    }
}
