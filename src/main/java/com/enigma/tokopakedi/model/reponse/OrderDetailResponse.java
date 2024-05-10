package com.enigma.tokopakedi.model.reponse;

import com.enigma.tokopakedi.entity.Product;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResponse {
    private String id;
    private String orderId;
    private Product product;
    private Long productPrice;
    private Integer quantity;

}
