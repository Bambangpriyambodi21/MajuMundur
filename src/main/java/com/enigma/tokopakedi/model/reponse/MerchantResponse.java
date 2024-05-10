package com.enigma.tokopakedi.model.reponse;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MerchantResponse {
    private String id;
    private String name;
    private String location;
    private String user_credential;
}
