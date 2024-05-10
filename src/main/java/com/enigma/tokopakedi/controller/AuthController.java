package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.model.request.AuthRequest;
import com.enigma.tokopakedi.model.reponse.UserResponse;
import com.enigma.tokopakedi.model.reponse.WebResponse;
import com.enigma.tokopakedi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request){
        UserResponse userCredential = authService.register(request);
        WebResponse<UserResponse> response = WebResponse.<UserResponse>builder()
                .message("Succesfully create new user")
                .status(HttpStatus.CREATED.getReasonPhrase())
                .data(userCredential)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(path = "/register/merchant")
    public ResponseEntity<?> registerAdmin(@RequestBody AuthRequest request){
        UserResponse userCredential = authService.registerAdmin(request);
        WebResponse<UserResponse> response = WebResponse.<UserResponse>builder()
                .message("Succesfully create new user admin")
                .status(HttpStatus.CREATED.getReasonPhrase())
                .data(userCredential)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request){
        String token = authService.login(request);
        WebResponse<String> response = WebResponse.<String>builder()
                .message("Succesfully login")
                .status(HttpStatus.CREATED.getReasonPhrase())
                .data(token)
                .build();
        return ResponseEntity.ok(response);
    }
}
