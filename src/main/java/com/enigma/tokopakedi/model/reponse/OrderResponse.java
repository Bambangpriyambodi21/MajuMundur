package com.enigma.tokopakedi.model.reponse;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse<T> {
    private String id;
    private String customerId;
    private Date transDate;
    private T orderDetails;
}
