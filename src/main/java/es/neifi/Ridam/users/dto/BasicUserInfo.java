package es.neifi.Ridam.users.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class BasicUserInfo {

    private UUID _id;
    private String username;
    private String first_name;
    private String last_name;
    private String gender;
    private String country;
    private String state;
    private String city;
    private String phone_number;
    private float rate;
    private String bio;
    private List<String> playable_instruments;
    private LocalDate registration_date;

}
