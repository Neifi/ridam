package es.neifi.Ridam.users.exceptions;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException() {
    }

    public UserNotFoundException(UUID uuid) {
        super("User not found");
    }
}
