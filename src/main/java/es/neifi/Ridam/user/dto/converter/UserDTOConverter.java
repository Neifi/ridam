package es.neifi.Ridam.user.dto.converter;

import es.neifi.Ridam.user.dto.BasicUserInfo;
import es.neifi.Ridam.user.dto.ContactUserInfo;
import es.neifi.Ridam.user.dto.LocationUserInfo;
import es.neifi.Ridam.user.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDTOConverter {
    private final ModelMapper modelMapper;

    public BasicUserInfo basicUserInfoConverter(User user) {
        return modelMapper.map(user, BasicUserInfo.class);
    }

    public ContactUserInfo contactUserInfoConverter(User user){
        return modelMapper.map(user,ContactUserInfo.class);
    }

    public LocationUserInfo locationUserInfoConverter(User user){
        return modelMapper.map(user,LocationUserInfo.class);
    }
}
