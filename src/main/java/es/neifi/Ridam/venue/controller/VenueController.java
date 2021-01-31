package es.neifi.Ridam.venue.controller;

import es.neifi.Ridam.venue.business.service.VenueService;
import es.neifi.Ridam.venue.model.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class VenueController {


    @Autowired
    private final VenueService venueService;


    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @GetMapping("/venues")
    public ResponseEntity<List<Venue>> getAllVenues() {

        List<Venue> result = this.venueService.getAllVenues();
        if (result.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/venue/{id}")
    public ResponseEntity<Venue> getById(@PathVariable UUID id) {
        Optional<Venue> result = this.venueService.getVenueById(id);
        return result.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/venue")
    public ResponseEntity<Venue> createVenue(@RequestBody Venue venue) {
        Optional<Venue> result = this.venueService.saveVenue(venue);
        return result.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/venue/{id}")
    public ResponseEntity<Venue> updateVenue(@PathVariable UUID id) {
        Optional<Venue> venueToFind = venueService.getVenueById(id);
        if (venueToFind.isPresent()) {
            return venueToFind
                    .map(venue -> ResponseEntity
                            .ok(venueService.uptadeVenue(venue).get()))
                    .orElseGet(() -> ResponseEntity.badRequest().build());

        }
        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/venue/{id}")
    public ResponseEntity<?> deleteVenue(@PathVariable UUID id) {
        this.venueService.getVenueById(id).ifPresent(u -> {
            this.venueService.deleteById(id);
        });
        return ResponseEntity.ok(id);
    }

}
