package es.neifi.Ridam.user.controller;

import es.neifi.Ridam.database.CollectionModel;
import es.neifi.Ridam.user.business.service.UserService;
import es.neifi.Ridam.user.dto.BasicUserInfo;
import es.neifi.Ridam.user.dto.converter.UserDTOConverter;
import es.neifi.Ridam.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class UserController {

    @Autowired
    private final UserService userService;
    @Autowired
    private UserDTOConverter userDTOConverter;


    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<BasicUserInfo>> getAllUsers(){

        List<User> result = this.userService.getAllUsers();
        if (result.isEmpty()) return ResponseEntity.noContent().build();
        List <BasicUserInfo> userInfos = result.stream().map(userDTOConverter::basicUserInfoConverter).collect(Collectors.toList());
        return ResponseEntity.ok(userInfos);
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<BasicUserInfo> getById(@PathVariable UUID id){
        Optional<User> result = this.userService.getUserById(id);
        if(result.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(userDTOConverter.basicUserInfoConverter(result.get()));
    }

    @PostMapping("/user")
    public ResponseEntity<BasicUserInfo> createUser(@RequestBody User user){
        Optional<User> result = this.userService.saveUser(user);

        if(result.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        //TODO 201 created response code
        return ResponseEntity.ok(userDTOConverter.basicUserInfoConverter(result.get()));
    }

    @PutMapping("/user")
    public ResponseEntity<BasicUserInfo> updateUser(@RequestBody User user){
        Optional<User> result = this.userService.updateUser(user);
        if(result.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(userDTOConverter.basicUserInfoConverter(result.get()));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<UUID> deleteUser(@PathVariable UUID id){
        this.userService.getUserById(id).ifPresent(u -> this.userService.deleteById(id));
        return ResponseEntity.ok(id);
    }

}
