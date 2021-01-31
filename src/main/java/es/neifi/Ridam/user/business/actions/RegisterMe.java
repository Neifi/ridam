package es.neifi.Ridam.user.business.actions;

import es.neifi.Ridam.user.dto.BasicUserInfo;
import es.neifi.Ridam.user.dto.ContactUserInfo;
import es.neifi.Ridam.user.dto.LocationUserInfo;
import es.neifi.Ridam.user.dto.converter.UserDTOConverter;
import es.neifi.Ridam.user.model.User;
import es.neifi.Ridam.user.repository.UserRepository;
import es.neifi.Ridam.user.repository.impl.MongoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Component
public class RegisterMe {

    @Autowired
    private final UserRepository repository;

    private final LocalDate registration_date = LocalDate.now();

    public RegisterMe(UserRepository repository){
        this.repository = repository;
    }

    public Optional<User> register(User user){
        user.setRegistration_date(this.registration_date);
        UUID _id = UUID.randomUUID();
        user.set_id(_id);

        return this.repository.save(user);

    }

   
    

}
