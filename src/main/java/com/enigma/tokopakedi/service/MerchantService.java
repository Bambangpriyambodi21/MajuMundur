package com.enigma.tokopakedi.service;

import com.enigma.tokopakedi.entity.Merchant;

import java.util.List;

public interface MerchantService {
    Merchant create(Merchant merchant);
    List<Merchant> getAll();
}
