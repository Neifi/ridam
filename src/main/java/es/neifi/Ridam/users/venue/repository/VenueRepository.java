package es.neifi.Ridam.users.venue.repository;

import es.neifi.Ridam.users.venue.model.Venue;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VenueRepository {

    public Optional<Venue> getById(UUID _id);
    public List<Venue> getAll();
    public Optional<Venue> save(Venue user);
    public Optional<Venue> update(Venue user);
    public int delete(UUID id);

}
