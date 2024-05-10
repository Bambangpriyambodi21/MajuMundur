package com.enigma.tokopakedi.model.reponse;

import com.enigma.tokopakedi.model.reponse.PagingResponse;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebResponse<T> {
    private String status;
    private String message;
    private PagingResponse paging;
    private T data;
}
