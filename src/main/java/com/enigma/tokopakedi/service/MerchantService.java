package com.enigma.tokopakedi.service;

import com.enigma.tokopakedi.entity.Merchant;
import com.enigma.tokopakedi.model.reponse.MerchantResponse;
import com.enigma.tokopakedi.model.request.MerchantRequest;

import java.util.List;

public interface MerchantService {
    MerchantResponse create(MerchantRequest merchant);
    List<MerchantResponse> getAll();
    MerchantResponse update(MerchantRequest merchant);
    String delete(String id);
    Merchant createNewMerchant(Merchant merchant);

}
