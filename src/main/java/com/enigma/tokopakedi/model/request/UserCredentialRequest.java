package com.enigma.tokopakedi.model.request;

import com.enigma.tokopakedi.entity.Role;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCredentialRequest {

    private String id;
    private List<Role> roles;
}
