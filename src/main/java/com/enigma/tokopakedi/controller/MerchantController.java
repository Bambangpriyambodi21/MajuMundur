package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.Merchant;
import com.enigma.tokopakedi.model.reponse.MerchantResponse;
import com.enigma.tokopakedi.model.reponse.WebResponse;
import com.enigma.tokopakedi.model.request.MerchantRequest;
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

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<MerchantResponse> all = merchantService.getAll();
        WebResponse<List<MerchantResponse>> response = WebResponse.<List<MerchantResponse>>builder()
                .message("Get all data merchant")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(all)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody MerchantRequest merchant){
        MerchantResponse update = merchantService.update(merchant);
        WebResponse<MerchantResponse> response = WebResponse.<MerchantResponse>builder()
                .message("Data updated")
                .status(HttpStatus.CREATED.getReasonPhrase())
                .data(update)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        String delete = merchantService.delete(id);
        WebResponse<String> response = WebResponse.<String>builder()
                .message("Data deleted")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(delete)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
