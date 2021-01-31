package es.neifi.Ridam.venue.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Venue {

    @MongoId
    private UUID _id;
    private String email;
    private String password;
    private String venue_name;
    private String country;
    private String state;
    private String city;
    private String phone_number;
    private float longitude;
    private float latitude;
    private float rate;
    private LocalDate registration_date;

}
