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
import java.util.stream.Collectors;

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

        List<MerchantResponse> merchantResponses = all.stream()
                .map(item -> MerchantResponse.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .location(item.getLocation())
                        .user_credential(item.getUserCredential().getId())
                        .build())
                .collect(Collectors.toList());

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

    @Override
    public Merchant createNewMerchant(Merchant merchant) {
        return merchantRepository.save(merchant);
    }
}
