package es.neifi.Ridam.users.dto.converter;

import es.neifi.Ridam.security.jwt.model.JwtUserResponse;
import es.neifi.Ridam.users.Rol;
import es.neifi.Ridam.users.User;
import es.neifi.Ridam.users.dto.BasicUserInfo;
import es.neifi.Ridam.users.dto.ContactUserInfo;
import es.neifi.Ridam.users.dto.LocationUserInfo;
import es.neifi.Ridam.users.musician.model.Musician;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserDTOConverter {
    private final ModelMapper modelMapper;

    public BasicUserInfo basicUserInfoConverter(User musician) {
        return modelMapper.map(musician, BasicUserInfo.class);
    }

    public ContactUserInfo contactUserInfoConverter(User musician){
        return modelMapper.map(musician,ContactUserInfo.class);
    }

    public LocationUserInfo locationUserInfoConverter(Musician musician){
        return modelMapper.map(musician,LocationUserInfo.class);
    }

    public JwtUserResponse tokenToUserResponse(User user, String token) {
        // TODO Auto-generated method stub
        return JwtUserResponse.jwtUserResponseBuilder()
                .rol(user.getRol().stream().map(Rol::name).collect(Collectors.toSet()))
                .token(token)
                .build();
    }
}
