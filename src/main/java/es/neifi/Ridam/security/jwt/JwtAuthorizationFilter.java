package es.neifi.Ridam.security.jwt;

import es.neifi.Ridam.users.User;
import es.neifi.Ridam.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Log
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtProvider provider;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Optional<String> token = getJwtFromRequest(request);
            if(token.isPresent()){
                if(StringUtils.hasText(token.get()) && provider.validateToken(token.get())) {
                    String _id = provider.getUserIdFromJWT(token.get());
                    //TODO if condition of optional
                     User user =  userService.getUserById(UUID.fromString(_id)).get();


                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, user.getRol(),user.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            }
        } catch (Exception e) {
            log.info("No se ha podido establecer la autenticación de usuario en el contexto de seguridad");
        }

        filterChain.doFilter(request, response);
    }

    private Optional<String> getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtProvider.TOKEN_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtProvider.TOKEN_PREFIX)) {
            return Optional.of(bearerToken.substring(JwtProvider.TOKEN_PREFIX.length()));
        }else {
            return Optional.empty();
        }
    }
}
