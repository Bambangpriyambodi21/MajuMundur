package com.enigma.tokopakedi.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest {
    @Email(message = "Invalid email")
    private String email;

    @Size(min = 6, message = "minimum password 6 character")
    private String password;
}
