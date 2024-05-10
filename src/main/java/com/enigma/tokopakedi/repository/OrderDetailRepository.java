package com.enigma.tokopakedi.repository;

import com.enigma.tokopakedi.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<TransactionDetail, String> {
}
