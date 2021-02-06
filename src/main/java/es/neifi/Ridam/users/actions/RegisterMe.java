package es.neifi.Ridam.users.actions;

import es.neifi.Ridam.users.User;
import es.neifi.Ridam.users.musician.model.Musician;
import es.neifi.Ridam.users.musician.repository.UserRepository;
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

    public Optional<User> register(User musician){
        musician.setRegistration_date(this.registration_date);
        UUID _id = UUID.randomUUID();
        musician.set_id(_id);

        return this.repository.save(musician);

    }

   
    

}
