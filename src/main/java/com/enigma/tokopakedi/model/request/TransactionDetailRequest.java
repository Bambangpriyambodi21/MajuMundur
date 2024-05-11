package com.enigma.tokopakedi.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDetailRequest {
    private String id;
    private String productId;
    private Integer quantity;
}
