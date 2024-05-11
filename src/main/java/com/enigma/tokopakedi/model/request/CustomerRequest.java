package com.enigma.tokopakedi.model.request;

import com.enigma.tokopakedi.entity.UserCredential;
import jakarta.persistence.OneToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequest {
    private String id;
    private String name;
    private String address;
    private String phone;
    private Integer poin;
    private String userCredential;
}
