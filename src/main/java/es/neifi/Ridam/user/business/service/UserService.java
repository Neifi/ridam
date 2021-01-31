package es.neifi.Ridam.user.business.service;

import es.neifi.Ridam.user.business.actions.RegisterMe;
import es.neifi.Ridam.user.dto.BasicUserInfo;
import es.neifi.Ridam.user.model.User;
import es.neifi.Ridam.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private final UserRepository repository;

    @Autowired
    private final RegisterMe registerMe;

    public UserService(UserRepository repository){
        this.repository = repository;
        registerMe = new RegisterMe(repository);
    }

    public List<User> getAllUsers(){
        return repository.getAll();
    }

    public Optional<User> getUserById(UUID id){
       return repository.getById(id);
    }

    public Optional<User> saveUser(User user){
        return this.registerMe.register(user);
    }

    public Optional<User> updateUser(User user){
        return repository.update(user);
    }

    public int deleteById(UUID id) {
        return this.repository.delete(id);
    }
}
