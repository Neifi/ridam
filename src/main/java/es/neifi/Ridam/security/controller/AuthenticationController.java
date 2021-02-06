package es.neifi.Ridam.security.controller;


import es.neifi.Ridam.security.jwt.JwtProvider;
import es.neifi.Ridam.security.jwt.model.LoginRequest;
import es.neifi.Ridam.users.User;
import es.neifi.Ridam.users.dto.converter.UserDTOConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider provider;
    private final UserDTOConverter converter;

    @PostMapping("auth/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest){

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail() , loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user =(User) authentication.getPrincipal();

        String token = provider.generateToken(authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(converter.tokenToUserResponse(user,token ));
    }
}
