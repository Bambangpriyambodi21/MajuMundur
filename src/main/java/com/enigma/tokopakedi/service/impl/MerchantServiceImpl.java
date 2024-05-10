package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.entity.Merchant;
import com.enigma.tokopakedi.entity.UserCredential;
import com.enigma.tokopakedi.model.reponse.MerchantResponse;
import com.enigma.tokopakedi.model.request.MerchantRequest;
import com.enigma.tokopakedi.repository.MerchantRepository;
import com.enigma.tokopakedi.service.AuthService;
import com.enigma.tokopakedi.service.MerchantService;
import com.enigma.tokopakedi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MerchantServiceImpl implements MerchantService {
    private final MerchantRepository merchantRepository;
    private final UserService userService;

    @Override
    public MerchantResponse create(MerchantRequest merchant) {
        UserCredential userCredential = userService.loadByUserId(merchant.getUser_credential());
        Merchant merchant1 = Merchant.builder()
                .name(merchant.getName())
                .location(merchant.getLocation())
                .userCredential(userCredential)
                .build();
        Merchant save = merchantRepository.save(merchant1);

        MerchantResponse merchantResponse = MerchantResponse.builder()
                .id(save.getId())
                .name(save.getName())
                .location(save.getLocation())
                .user_credential(save.getUserCredential().getId())
                .build();

        return merchantResponse;
    }

    @Override
    public List<MerchantResponse> getAll() {
        List<Merchant> all = merchantRepository.findAll();

        List<MerchantResponse> merchantResponses = new ArrayList<>();
        for (int i = 0; i<all.size();i++){
            MerchantResponse merchantResponse = MerchantResponse.builder()
                    .id(all.get(i).getId())
                    .name(all.get(i).getName())
                    .location(all.get(i).getLocation())
                    .user_credential(all.get(i).getUserCredential().getId())
                    .build();
            merchantResponses.add(merchantResponse);
        }
        return merchantResponses;

    }

    @Override
    public MerchantResponse update(MerchantRequest merchant) {
        Optional<Merchant> byId = merchantRepository.findById(merchant.getId());
        if (byId.isEmpty()) throw new RuntimeException("Merchant not found");
        UserCredential userCredential = userService.loadByUserId(merchant.getUser_credential());
        if (userCredential==null) throw new RuntimeException("User Credential not found");

        Merchant merchant1 = Merchant.builder()
                .id(merchant.getId())
                .name(merchant.getName())
                .location(merchant.getLocation())
                .userCredential(userCredential)
                .build();

        Merchant merchant2 = merchantRepository.saveAndFlush(merchant1);
        MerchantResponse merchantResponse = MerchantResponse.builder()
                .id(merchant2.getId())
                .name(merchant2.getName())
                .location(merchant2.getLocation())
                .user_credential(merchant2.getUserCredential().getId())
                .build();

        return merchantResponse;
    }

    @Override
    public String delete(String id) {
        if (merchantRepository.findById(id).isEmpty()) throw new RuntimeException("Merchant not found");
        merchantRepository.deleteById(id);
        return "Merchant deleted";
    }
}
