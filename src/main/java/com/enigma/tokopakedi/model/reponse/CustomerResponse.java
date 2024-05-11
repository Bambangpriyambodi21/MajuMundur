package com.enigma.tokopakedi.model.reponse;

import com.enigma.tokopakedi.entity.UserCredential;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse<T> {
    private String id;
    private String name;
    private String address;
    private String phone;
    private Integer poin;
    private String reward;
    private T userCredential;
}
