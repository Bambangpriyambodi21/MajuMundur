package com.enigma.tokopakedi.model.request;

import com.enigma.tokopakedi.model.request.OrderDetailRequest;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    private String customerId;
    private List<OrderDetailRequest> orderDetails;
}
