package es.neifi.Ridam.user.repository;

import es.neifi.Ridam.user.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    public Optional<User> getById(UUID id);
    public List<User> getAll();
    public Optional<User> save(User user);
    public Optional<User> update(User user);
    public int delete(UUID id);

}
