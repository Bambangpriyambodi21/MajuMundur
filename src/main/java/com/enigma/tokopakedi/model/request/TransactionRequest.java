package com.enigma.tokopakedi.model.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {
    private String id;
    private String customerId;
    private List<TransactionDetailRequest> orderDetails;
}
