package es.neifi.Ridam.users.services;

import es.neifi.Ridam.users.User;
import es.neifi.Ridam.users.UserService;
import es.neifi.Ridam.users.Users;
import es.neifi.Ridam.users.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("UserDetailService")
@RequiredArgsConstructor
public class CustomUsersDetailsService implements UserDetailsService{
    private final UserService userService;


    public User loadUserById(UUID _id) {
        return userService.getUserById(_id)
                .orElseThrow(() -> new UserNotFoundException(_id));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userService.getUserByUsername(s).orElseThrow(() -> new UserNotFoundException(s));

    }
}
