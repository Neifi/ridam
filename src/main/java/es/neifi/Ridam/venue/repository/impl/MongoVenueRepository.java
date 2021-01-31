package es.neifi.Ridam.venue.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.result.DeleteResult;
import es.neifi.Ridam.user.model.User;
import es.neifi.Ridam.venue.model.Venue;
import es.neifi.Ridam.venue.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MongoVenueRepository implements VenueRepository {

    @Autowired
    private final MongoOperations mongoOperations;

    public MongoVenueRepository(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }


    @Override
    public Optional<Venue> getById(UUID _id) {
        return Optional.ofNullable(mongoOperations
                .findOne(new Query(Criteria.where("_id")
                        .is(_id)), Venue.class));
    }

    @Override
    public List<Venue> getAll() {
        return mongoOperations.findAll(Venue.class);
    }

    @Override
    public Optional<Venue> save(Venue venue) {
        return Optional.of(mongoOperations.save(venue));
    }

    @Override
    public Optional<Venue> update(Venue venue) {
        ObjectMapper objectMapper = new ObjectMapper();
        //TODO refactor
        Map<String, Object> objectMap = objectMapper.convertValue(venue, Map.class);
        objectMap.values().removeIf(Objects::isNull);
        Update update = new Update();
        objectMap.forEach(update::set);

        return Optional.ofNullable(mongoOperations.findAndModify(
                Query.query(Criteria.where("id")
                        .is(venue.get_id())), update, Venue.class));
    }

    @Override
    public int delete(UUID id) {
        DeleteResult deleteResult = mongoOperations
                .remove(Query.query(Criteria.where("_id").is(id)));
        if (deleteResult.getDeletedCount() == 1) {

            return 1;
        }
        return -1;
    }
}
