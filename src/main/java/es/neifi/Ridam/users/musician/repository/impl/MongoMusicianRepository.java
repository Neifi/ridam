package es.neifi.Ridam.users.musician.repository.impl;

import com.mongodb.client.result.UpdateResult;
import es.neifi.Ridam.users.User;
import es.neifi.Ridam.users.dto.converter.UserDTOConverter;
import es.neifi.Ridam.users.musician.model.Musician;
import es.neifi.Ridam.users.musician.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

@Repository
public class MongoMusicianRepository implements UserRepository {

    @Autowired
    private final MongoOperations mongoOperations;

    @Autowired
    private final UserDTOConverter userDTOConverter;

    public MongoMusicianRepository(MongoOperations mongoOperations, UserDTOConverter userDTOConverter) {
        this.mongoOperations = mongoOperations;
        this.userDTOConverter = userDTOConverter;
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
    public int update(User musician) {

        Update userToUpdate = new Update();
        Query query = Query.query(Criteria.where("_id").is(musician.get_id()));

        try {
            setUpdateValues(musician, userToUpdate);

            UpdateResult updateResult = mongoOperations.updateFirst(query,userToUpdate,User.class);

            if(updateResult.getModifiedCount() > 0 && updateResult.getModifiedCount() < 2){
                return 1;
            }else if(updateResult.getModifiedCount() > 2){
                return 0;
            }


        }catch (Exception e){
            e.printStackTrace();
        }


       return -1;
    }

    private void setUpdateValues(User musician, Update userToUpdate) throws Exception {
        Map<String, Object> userData = introspect(musician);



        for (Map.Entry<String, Object> entry : userData.entrySet()) {
            if(
                    //fuck this expression TODO must be refactored or remove introspect function
                    entry.getKey().equals( "first_name")||
                            entry.getKey().equals( "last_name")||
                            entry.getKey().equals( "gender")||
                            entry.getKey().equals( "is_band")||
                            entry.getKey().equals( "_id")||
                            entry.getKey().equals( "email")||
                            entry.getKey().equals( "password")||
                            entry.getKey().equals( "country")||
                            entry.getKey().equals( "state")||
                            entry.getKey().equals( "city")||
                            entry.getKey().equals( "phone_number")||
                            entry.getKey().equals( "longitude")||
                            entry.getKey().equals( "latitude")||
                            entry.getKey().equals( "rate")||
                            entry.getKey().equals( "bio")||
                            entry.getKey().equals( "register_date")||
                            entry.getKey().equals( "rol")
            ){

                if(entry.getValue() != null) {
                    System.out.println(entry.getKey() + "/" + entry.getValue());
                    userToUpdate.set(entry.getKey(), entry.getValue());
                }
            }
        }
        userToUpdate.set("rate", musician.getRate());
        userToUpdate.set("latitude", musician.getLatitude());
        userToUpdate.set("longitude", musician.getLongitude());


    }

    public  Map<String, Object> introspect(Object obj) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        BeanInfo info = Introspector.getBeanInfo(obj.getClass());


        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            Method reader = pd.getReadMethod();
            if (reader != null)
                result.put(pd.getName(), reader.invoke(obj));
        }
        return result;
    }

    @Override
    public int delete(UUID id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        User deleteResult = mongoOperations.findAndRemove(query,User.class);

        if (deleteResult != null) {

            return 1;
        }

        return -1;
    }


}
