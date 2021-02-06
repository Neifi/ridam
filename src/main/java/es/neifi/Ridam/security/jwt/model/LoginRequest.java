package es.neifi.Ridam.security.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
