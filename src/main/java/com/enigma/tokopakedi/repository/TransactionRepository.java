package com.enigma.tokopakedi.repository;

import com.enigma.tokopakedi.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
