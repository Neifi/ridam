package es.neifi.Ridam.users.musician.controller;

import es.neifi.Ridam.users.User;
import es.neifi.Ridam.users.services.UserServiceImpl;
import es.neifi.Ridam.users.dto.BasicUserInfo;
import es.neifi.Ridam.users.dto.converter.UserDTOConverter;
import es.neifi.Ridam.users.musician.model.Musician;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private final UserServiceImpl userServiceImpl;
    @Autowired
    private UserDTOConverter userDTOConverter;


    public UserController(UserServiceImpl userServiceImpl){
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/users")
    public ResponseEntity<List<BasicUserInfo>> getAllUsers(){

        List<User> result = this.userServiceImpl.getAllUsers();
        if (result.isEmpty()) return ResponseEntity.noContent().build();
        List <BasicUserInfo> userInfos = result.stream().map(userDTOConverter::basicUserInfoConverter).collect(Collectors.toList());
        return ResponseEntity.ok(userInfos);
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<BasicUserInfo> getById(@PathVariable UUID id){
        Optional<User> result = this.userServiceImpl.getUserById(id);
        if(result.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(userDTOConverter.basicUserInfoConverter(result.get()));
    }

    @PostMapping("/user")
    public ResponseEntity<BasicUserInfo> createUser(@RequestBody Musician musician){
        Optional<User> result = this.userServiceImpl.saveUser(musician);

        if(result.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        //TODO 201 created response code
        return ResponseEntity.ok(userDTOConverter.basicUserInfoConverter(result.get()));
    }

    @PutMapping("/user")
    public ResponseEntity<BasicUserInfo> updateUser(@RequestBody Musician musician){
        Optional<User> result = this.userServiceImpl.updateUser(musician);
        if(result.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(userDTOConverter.basicUserInfoConverter(result.get()));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<UUID> deleteUser(@PathVariable UUID id){
        final int []result = new int[1];
        this.userServiceImpl.getUserById(id).ifPresent(u ->
                result[0] = this.userServiceImpl.deleteById(id)
        );
        if(result[0] == 1) return ResponseEntity.ok(id);
        return ResponseEntity.badRequest().build();
    }

}
