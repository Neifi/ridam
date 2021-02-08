package es.neifi.Ridam.users.services;

import es.neifi.Ridam.users.User;
import es.neifi.Ridam.users.UserService;
import es.neifi.Ridam.users.actions.RegisterMe;
import es.neifi.Ridam.users.musician.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository repository;

    @Autowired
    private final RegisterMe registerMe;

    public UserServiceImpl(UserRepository repository){
        this.repository = repository;
        registerMe = new RegisterMe(repository);
    }

    @Override
    public List<User> getAllUsers(){
        return repository.getAll();
    }

    @Override
    public Optional<User> getUserById(UUID id){
       return repository.getById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String email) {

        return repository.getByEmail(email);
    }

    @Override
    public Optional<User> saveUser(User musician){
        return this.registerMe.register(musician);
    }

    @Override
    public int updateUser(User musician){
        return repository.update(musician);
    }

    @Override
    public int deleteById(UUID id) {
        return this.repository.delete(id);
    }
}
