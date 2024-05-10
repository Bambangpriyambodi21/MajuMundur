package com.enigma.tokopakedi.model.reponse;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagingResponse<T> {
    private Integer page;
    private Integer size;
    private Integer totalPage;
    private Long totalElements;
}
