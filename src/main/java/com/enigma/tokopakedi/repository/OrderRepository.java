package com.enigma.tokopakedi.repository;

import com.enigma.tokopakedi.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Transaction, String> {
}
