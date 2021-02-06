package es.neifi.Ridam.security.jwt.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class JwtUserResponse {

    private String token;

    @Builder(builderMethodName = "jwtUserResponseBuilder")
    public JwtUserResponse(Set<String> rol, String token) {

        this.token = token;
    }

}
