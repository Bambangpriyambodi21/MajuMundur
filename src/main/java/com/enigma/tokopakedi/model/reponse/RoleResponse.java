package com.enigma.tokopakedi.model.reponse;

import com.enigma.tokopakedi.constant.ERole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleResponse {
    private String id;
    private List<ERole> role;
}
