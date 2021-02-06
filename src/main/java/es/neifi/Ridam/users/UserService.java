package es.neifi.Ridam.users;

import es.neifi.Ridam.users.musician.model.Musician;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface UserService {

    public List<User> getAllUsers();

    public Optional<User> getUserById(UUID id);

    public Optional<User> getUserByUsername(String email);

    public Optional<User> saveUser(User user);

    public Optional<User> updateUser(User user);

    public int deleteById(UUID id);




}


