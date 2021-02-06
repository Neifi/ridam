package es.neifi.Ridam.users.musician.repository;

import es.neifi.Ridam.users.User;
import es.neifi.Ridam.users.musician.model.Musician;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository {

    public Optional<User> getById(UUID id);
    public List<User> getAll();
    public Optional<User> save(User user);
    public Optional<User> update(User user);
    public int delete(UUID id);

}
