package es.neifi.Ridam.user.business.service;

import com.google.inject.internal.util.ImmutableList;
import es.neifi.Ridam.user.dto.BasicUserInfo;
import es.neifi.Ridam.user.model.User;
import es.neifi.Ridam.user.repository.impl.MongoUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private MongoUserRepository mongoUserRepository;

    private UserService userService;

    private User userOne,userTwo,userThree;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(mongoUserRepository);
        userOne = User.builder()
                .first_name("joan")
                .last_name("fonts")
                .username("Joan Font")
                .email("joanfonts@gmail.com")
                .password("secret")
                .country("spain")
                .state("catalonia")
                .city("Barcelona")
                .phone_number("(+34)638194105")
                .gender("M")
                .registration_date(LocalDate.now())
                .rate(3.8F)
                .is_band(false)
                .latitude(41.390205F)
                .longitude(2.154007F)
                .build();

        userTwo = User.builder()
                .first_name("Marc")
                .last_name("Solans")
                .username("Marc drums")
                .email("msdrums@gmail.com")
                .password("secret")
                .country("spain")
                .state("catalonia")
                .city("Barcelona")
                .phone_number("(+34)638194105")
                .gender("M")
                .registration_date(LocalDate.now())
                .rate(3.8F)
                .is_band(false)
                .latitude(41.390205F)
                .longitude(2.154007F)
                .build();
        userThree = User.builder()
                .username("The Five Guys")
                .email("thefiveguys@gmail.com")
                .password("secret")
                .country("spain")
                .state("catalonia")
                .city("Barcelona")
                .phone_number("(+34)638194105")
                .registration_date(LocalDate.now())
                .rate(4.8F)
                .is_band(true)
                .latitude(41.390205F)
                .longitude(2.154007F)
                .build();
    }

    @Test
    void shouldGetAllUsers() {
        userOne.set_id(UUID.randomUUID());
        userTwo.set_id(UUID.randomUUID());
        userThree.set_id(UUID.randomUUID());

        ImmutableList<User> users = new ImmutableList.Builder<User>()
            .add(userOne).add(userTwo).add(userThree).build();

        given(mongoUserRepository.getAll()).willReturn(users);
        List<User> allUsers = userService.getAllUsers();
        assertFalse(allUsers.isEmpty());
        assertEquals(allUsers.size(),3);
        assertEquals(users.get(0),userOne);
        assertEquals(users.get(1),userTwo);
        assertEquals(users.get(2),userThree);

    }

    @Test
    void shouldGetUserById() {
        UUID _id = UUID.randomUUID();
        userOne.set_id(_id);

        given(mongoUserRepository.getById(_id)).willReturn(Optional.of(userOne));
        Optional<User> expectedUser = userService.getUserById(_id);

        assertTrue(expectedUser.isPresent());
        assertEquals(expectedUser.get(),userOne);
    }

    @Test
    void shouldSaveUser() {
        UUID _id = UUID.randomUUID();
        userOne.set_id(_id);
        given(mongoUserRepository.save(userOne)).willReturn(Optional.of(userOne));
        Optional<User> expectedUser = userService.saveUser(userOne);

        assertTrue(expectedUser.isPresent());
        assertEquals(userOne,expectedUser.get());
    }

    @Test
    void shouldUpdateUser() {
        UUID _id = UUID.randomUUID();
        userOne.set_id(_id);
        userOne.setEmail("joan_fonts@gmail.com");

        given(mongoUserRepository.update(userOne)).willReturn(Optional.of(userOne));

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        Optional<User> expectedUser = userService.updateUser(userOne);

        verify(mongoUserRepository).update(captor.capture());
        User user = captor.getValue();

        assertTrue(expectedUser.isPresent());
        assertEquals(user,expectedUser.get());


    }

    @Test
    void shouldDeleteById() {
        UUID _id = UUID.randomUUID();
        userOne.set_id(_id);

        given(mongoUserRepository.delete(_id)).willReturn(1);

        int deleteResult = userService.deleteById(_id);
        assertEquals(1,deleteResult);

    }
}