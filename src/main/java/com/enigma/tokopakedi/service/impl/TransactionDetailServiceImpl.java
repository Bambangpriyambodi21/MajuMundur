package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.entity.TransactionDetail;
import com.enigma.tokopakedi.repository.TransactionDetailRepository;
import com.enigma.tokopakedi.service.TransactionDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionDetailServiceImpl implements TransactionDetailService {

    private final TransactionDetailRepository transactionDetailRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransactionDetail createOrUpdate(TransactionDetail transactionDetail) {
        return transactionDetailRepository.save(transactionDetail);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String delete(String id) {
        transactionDetailRepository.deleteById(id);
        return null;
    }
}
