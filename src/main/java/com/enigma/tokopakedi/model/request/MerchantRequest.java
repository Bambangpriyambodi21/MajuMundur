package com.enigma.tokopakedi.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MerchantRequest {
    private String id;
    private String name;
    private String location;
    private String user_credential;
}
