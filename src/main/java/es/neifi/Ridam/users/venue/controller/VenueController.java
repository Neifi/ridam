package es.neifi.Ridam.users.venue.controller;

import es.neifi.Ridam.users.User;
import es.neifi.Ridam.users.services.UserServiceImpl;
import es.neifi.Ridam.users.venue.model.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class VenueController {


    @Autowired
    private final UserServiceImpl venueService;


    public VenueController(UserServiceImpl venueService) {
        this.venueService = venueService;
    }

    @GetMapping("/venues")
    public ResponseEntity<List<User>> getAllVenues() {

        List<User> result = this.venueService.getAllUsers();
        if (result.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/venue/{id}")
    public ResponseEntity<User> getById(@PathVariable UUID id) {
        Optional<User> result = this.venueService.getUserById(id);
        return result.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/venue")
    public ResponseEntity<User> createVenue(@RequestBody Venue venue) {
        Optional<User> result = this.venueService.saveUser(venue);
        return result.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/venue/{id}")
    public ResponseEntity<User> updateVenue(@PathVariable UUID id) {
        Optional<User> venueToFind = venueService.getUserById(id);
        if (venueToFind.isPresent()) {
            return venueToFind
                    .map(venue -> ResponseEntity
                            .ok(venueService.getUserById(id).get()))
                    .orElseGet(() -> ResponseEntity.badRequest().build());

        }
        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/venue/{id}")
    public ResponseEntity<Integer> deleteVenue(@PathVariable UUID id) {
        final int[] result = new int[1];
        this.venueService.getUserById(id).ifPresent(u -> {
            result[0] = this.venueService.deleteById(id);

        });
        if (result[0] == 1)ResponseEntity.ok(id);
        return ResponseEntity.badRequest().build();
    }

}
