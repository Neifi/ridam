package es.neifi.Ridam.users.musician.business.service;

import com.google.inject.internal.util.ImmutableList;
import es.neifi.Ridam.users.User;
import es.neifi.Ridam.users.musician.model.Musician;
import es.neifi.Ridam.users.musician.repository.impl.MongoUserRepository;
import es.neifi.Ridam.users.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private MongoUserRepository mongoUserRepository;

    private UserServiceImpl userServiceImpl;

    private Musician musicianOne, musicianTwo, musicianThree;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userServiceImpl = new UserServiceImpl(mongoUserRepository);
        musicianOne = Musician.builder()
                .first_name("joan")
                .last_name("fonts")
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

        musicianTwo = Musician.builder()
                .first_name("Marc")
                .last_name("Solans")
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
        musicianThree = Musician.builder()
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
        musicianOne.set_id(UUID.randomUUID());
        musicianTwo.set_id(UUID.randomUUID());
        musicianThree.set_id(UUID.randomUUID());

        ImmutableList<User> musicians = new ImmutableList.Builder<User>()
            .add(musicianOne).add(musicianTwo).add(musicianThree).build();

        given(mongoUserRepository.getAll()).willReturn(musicians);
        List<User> allMusicians = userServiceImpl.getAllUsers();
        assertFalse(allMusicians.isEmpty());
        assertEquals(allMusicians.size(),3);
        assertEquals(musicians.get(0), musicianOne);
        assertEquals(musicians.get(1), musicianTwo);
        assertEquals(musicians.get(2), musicianThree);

    }

    @Test
    void shouldGetUserById() {
        UUID _id = UUID.randomUUID();
        musicianOne.set_id(_id);

        given(mongoUserRepository.getById(_id)).willReturn(Optional.of(musicianOne));
        Optional<User> expectedUser = userServiceImpl.getUserById(_id);

        assertTrue(expectedUser.isPresent());
        assertEquals(expectedUser.get(), musicianOne);
    }

    @Test
    void shouldSaveUser() {
        UUID _id = UUID.randomUUID();
        musicianOne.set_id(_id);
        given(mongoUserRepository.save(musicianOne)).willReturn(Optional.of(musicianOne));
        Optional<User> expectedUser = userServiceImpl.saveUser(musicianOne);

        assertTrue(expectedUser.isPresent());
        assertEquals(musicianOne,expectedUser.get());
    }

    @Test
    void shouldUpdateUser() {
        UUID _id = UUID.randomUUID();
        musicianOne.set_id(_id);
        musicianOne.setEmail("joan_fonts@gmail.com");

        given(mongoUserRepository.update(musicianOne)).willReturn(Optional.of(musicianOne));

        ArgumentCaptor<Musician> captor = ArgumentCaptor.forClass(Musician.class);
        Optional<User> expectedUser = userServiceImpl.updateUser(musicianOne);

        verify(mongoUserRepository).update(captor.capture());
        Musician musician = captor.getValue();

        assertTrue(expectedUser.isPresent());
        assertEquals(musician,expectedUser.get());


    }

    @Test
    void shouldDeleteById() {
        UUID _id = UUID.randomUUID();
        musicianOne.set_id(_id);

        given(mongoUserRepository.delete(_id)).willReturn(1);

        int deleteResult = userServiceImpl.deleteById(_id);
        assertEquals(1,deleteResult);

    }
}