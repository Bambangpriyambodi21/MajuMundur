package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.entity.Merchant;
import com.enigma.tokopakedi.repository.MerchantRepository;
import com.enigma.tokopakedi.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService {
    private final MerchantRepository merchantRepository;

    @Override
    public Merchant create(Merchant merchant) {
        return merchantRepository.save(merchant);
    }

    @Override
    public List<Merchant> getAll() {
        return merchantRepository.findAll();
    }
}
