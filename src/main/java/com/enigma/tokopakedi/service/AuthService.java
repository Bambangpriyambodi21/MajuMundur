package com.enigma.tokopakedi.service;

import com.enigma.tokopakedi.model.request.AuthRequest;
import com.enigma.tokopakedi.model.reponse.UserResponse;

public interface AuthService {
    UserResponse register(AuthRequest request);
    UserResponse registerAdmin(AuthRequest request);
    String login(AuthRequest request);
}
