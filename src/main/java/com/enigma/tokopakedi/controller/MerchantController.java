package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.Merchant;
import com.enigma.tokopakedi.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/merchant")
@RequiredArgsConstructor
public class MerchantController {
    private final MerchantService merchantService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Merchant merchant){
        Merchant merchant1 = merchantService.create(merchant);
        return ResponseEntity.status(HttpStatus.CREATED).body(merchant1);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Merchant> all = merchantService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(all);
    }
}
