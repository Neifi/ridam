package es.neifi.Ridam.venue.repository;

import es.neifi.Ridam.user.model.User;
import es.neifi.Ridam.venue.model.Venue;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VenueRepository {

    public Optional<Venue> getById(UUID _id);
    public List<Venue> getAll();
    public Optional<Venue> save(Venue user);
    public Optional<Venue> update(Venue user);
    public int delete(UUID id);

}
