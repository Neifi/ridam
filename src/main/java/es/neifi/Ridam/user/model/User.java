package es.neifi.Ridam.user.model;

import es.neifi.Ridam.database.CollectionModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Document(collection = CollectionModel.USER)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {


    private UUID _id;
    private String email;
    private String password;
    private String username;
    private String first_name;
    private String last_name;
    private String gender;
    private String country;
    private String state;
    private String city;
    private String phone_number;
    private float longitude;
    private float latitude;
    private boolean is_band;
    private float rate;
    private LocalDate registration_date;
    private String bio;
    private List<String> playable_instruments;

}
