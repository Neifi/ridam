package es.neifi.Ridam.users.musician.model;

import es.neifi.Ridam.database.CollectionModel;
import es.neifi.Ridam.users.Rol;
import es.neifi.Ridam.users.User;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Document(collection = CollectionModel.USER)
@Getter
@Setter
public class Musician extends User {

    private String first_name;
    private String last_name;
    private String gender;
    private boolean is_band;

    private List<String> playable_instruments;

    public Musician(){}

    @Builder

    public Musician(UUID _id, String email, String password,
                    String country, String state, String city,
                    String phone_number, float longitude, float latitude,
                    float rate, String bio, LocalDate registration_date,
                    Set<Rol> rol, String first_name, String last_name,
                    String gender, boolean is_band,
                    List<String> playable_instruments) {
        super(_id, email, password, country, state, city, phone_number, longitude, latitude, rate, bio, registration_date, rol);
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.is_band = is_band;
        this.playable_instruments = playable_instruments;
    }
}
