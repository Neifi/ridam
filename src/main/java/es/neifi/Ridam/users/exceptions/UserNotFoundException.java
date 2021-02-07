package es.neifi.Ridam.users.exceptions;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException() {
        
    }

    public UserNotFoundException(UUID uuid) {
        super("User not found with id: "+ uuid);
    }

    public UserNotFoundException(String email){
        super("User not found with email: "+ email);

    }

}
