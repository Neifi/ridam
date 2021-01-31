package es.neifi.Ridam.user.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.result.DeleteResult;
import es.neifi.Ridam.user.model.User;
import es.neifi.Ridam.user.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MongoUserRepository implements UserRepository {

    @Autowired
    private final MongoOperations mongoOperations;

    public MongoUserRepository(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public Optional<User> getById(UUID id) {

        return Optional.ofNullable(
                mongoOperations.findOne(new Query(Criteria.where("_id").is(id)),User.class));

    }

    @Override
    public List<User> getAll() {
        return mongoOperations.findAll(User.class);
    }

    @Override
    public Optional<User> save(User user) {
        return Optional.of(mongoOperations.save(user));
    }

    @Override
    public Optional<User> update(User user) {
        ObjectMapper objectMapper = new ObjectMapper();
        //TODO refactor
        Map<String, String> objectMap = objectMapper.convertValue(user, Map.class);
        objectMap.values().removeIf(Objects::isNull);
        Update update = new Update();
        objectMap.forEach(update::set);


        return Optional.ofNullable(mongoOperations.findAndModify(
                Query.query(Criteria.where("id").is(user.get_id())), update, User.class));
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
