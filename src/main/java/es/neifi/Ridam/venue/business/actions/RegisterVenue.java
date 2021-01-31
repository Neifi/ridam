package es.neifi.Ridam.venue.business.actions;

import es.neifi.Ridam.venue.model.Venue;
import es.neifi.Ridam.venue.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public class RegisterVenue {

    @Autowired
    private final VenueRepository repository;

    private final LocalDate registration_date = LocalDate.now();

    public RegisterVenue(VenueRepository repository) {
        this.repository = repository;
    }


    public Optional<Venue> register(Venue venue) {
        venue.setRegistration_date(this.registration_date);
        UUID _id = UUID.randomUUID();
        venue.set_id(_id);
        return this.repository.save(venue);

    }
}
