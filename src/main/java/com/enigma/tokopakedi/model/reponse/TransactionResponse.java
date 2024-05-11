package com.enigma.tokopakedi.model.reponse;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse<T> {
    private String id;
    private String customerId;
    private Date transDate;
    private T orderDetails;
}
