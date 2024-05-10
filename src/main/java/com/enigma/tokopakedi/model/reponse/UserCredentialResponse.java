package com.enigma.tokopakedi.model.reponse;

import com.enigma.tokopakedi.entity.Role;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCredentialResponse {
    private String id;
    private String email;
    private List<Role> roles;
}
