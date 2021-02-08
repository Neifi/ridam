package es.neifi.Ridam.users.musician.repository;

import es.neifi.Ridam.users.User;
import es.neifi.Ridam.users.musician.model.Musician;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository {

    Optional<User> getById(UUID id);
    Optional<User> getByEmail(String email);
    List<User> getAll();
    Optional<User> save(User user);
    int update(User user);
    int delete(UUID id);

}
