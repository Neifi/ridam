package es.neifi.Ridam.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.neifi.Ridam.security.jwt.error.AuthorizationError;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper mapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");

        AuthorizationError authorizationError = new AuthorizationError(HttpStatus.UNAUTHORIZED, authException.getMessage());
        String strApiError = mapper.writeValueAsString(authorizationError);
        PrintWriter writer = response.getWriter();

        writer.println(strApiError);

    }


}
