package es.neifi.Ridam.users.dto;

import es.neifi.Ridam.users.Rol;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public class MusicianDTO {

    private String first_name;
    private String last_name;
    private String gender;
    private boolean is_band;

    private UUID _id;
    private String email;
    private String password;
    private String country;
    private String state;
    private String city;
    private String phone_number;
    private float longitude;
    private float latitude;
    private float rate;
    private String bio;
    private LocalDate registration_date;
    private Set<Rol> rol;
}
