package es.neifi.Ridam.users.venue.model;

import es.neifi.Ridam.database.CollectionModel;
import es.neifi.Ridam.users.Rol;
import es.neifi.Ridam.users.User;
import es.neifi.Ridam.users.musician.model.Musician;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@Builder
@Document(collection = CollectionModel.VENUE)
public class Venue extends User {

    private String venue_name;
    private String proprietary_name;
    private String proprietary_last_name;

    public Venue(UUID _id, String email, String password, String country,
                 String state, String city, String phone_number, float longitude,
                 float latitude, float rate, String bio, LocalDate registration_date,
                 String venue_name, String proprietary_name,
                 String proprietary_last_name, Set<Rol> role) {
        super(_id, email, password, country, state, city, phone_number,
                longitude, latitude, rate, bio, registration_date,role);
        this.venue_name = venue_name;
        this.proprietary_name = proprietary_name;
        this.proprietary_last_name = proprietary_last_name;
    }

    public Venue(String venue_name, String proprietary_name, String proprietary_last_name) {
        this.venue_name = venue_name;
        this.proprietary_name = proprietary_name;
        this.proprietary_last_name = proprietary_last_name;
    }
}
