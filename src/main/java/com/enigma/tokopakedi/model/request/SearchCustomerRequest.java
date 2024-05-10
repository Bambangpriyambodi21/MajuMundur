package com.enigma.tokopakedi.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchCustomerRequest {
    private Integer page;
    private Integer size;
    private String name;
    private String phone;
}
