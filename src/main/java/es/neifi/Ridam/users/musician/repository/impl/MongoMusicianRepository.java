package es.neifi.Ridam.users.musician.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.result.DeleteResult;
import es.neifi.Ridam.users.User;
import es.neifi.Ridam.users.musician.model.Musician;
import es.neifi.Ridam.users.musician.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MongoMusicianRepository implements UserRepository {

    @Autowired
    private final MongoOperations mongoOperations;

    public MongoMusicianRepository(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public Optional<User> getById(UUID id) {

        return Optional.ofNullable(
                mongoOperations.findOne(new Query(Criteria.where("_id").is(id)), Musician.class));

    }

    @Override
    public Optional<User> getByEmail(String email) {
        return Optional.ofNullable(
                mongoOperations
                        .findOne(
                                new Query(Criteria.where("email").is(email)), Musician.class));
    }

    @Override
    public List<User> getAll() {
        return mongoOperations.findAll(User.class);
    }

    @Override
    public Optional<User> save(User musician) {
        return Optional.of(mongoOperations.save(musician));
    }

    @Override
    public Optional<User> update(User musician) {
        ObjectMapper objectMapper = new ObjectMapper();
        //TODO refactor
        Map<String, String> objectMap = objectMapper.convertValue(musician, Map.class);
        objectMap.values().removeIf(Objects::isNull);
        Update update = new Update();
        objectMap.forEach(update::set);


        return Optional.ofNullable(mongoOperations.findAndModify(
                Query.query(Criteria.where("id").is(musician.get_id())), update, Musician.class));
    }

    @Override
    public int delete(UUID id) {
        DeleteResult deleteResult = mongoOperations.remove(Query.query(Criteria.where("_id").is(id)));
        if (deleteResult.getDeletedCount() == 1) {

            return 1;
        }

        return -1;
    }


}
