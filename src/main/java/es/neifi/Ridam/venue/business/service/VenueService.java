package es.neifi.Ridam.venue.business.service;

import es.neifi.Ridam.venue.business.actions.RegisterVenue;
import es.neifi.Ridam.venue.model.Venue;
import es.neifi.Ridam.venue.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VenueService {

    @Autowired
    private final VenueRepository repository;
    private final RegisterVenue registerVenue;


    public VenueService(VenueRepository repository) {
        registerVenue = new RegisterVenue(repository);
        this.repository = repository;
    }

    public List<Venue> getAllVenues(){
        return repository.getAll();
    }

    public Optional<Venue> getVenueById(UUID id){
        return repository.getById(id);
    }

    public Optional<Venue> saveVenue(Venue venue){
        return this.registerVenue.register(venue);
    }

    public Optional<Venue> uptadeVenue(Venue venue){
        return repository.update(venue);
    }

    public int deleteById(UUID id) {
        return this.repository.delete(id);
    }


}
