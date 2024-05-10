package com.enigma.tokopakedi.repository;

import com.enigma.tokopakedi.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant, String> {
}
