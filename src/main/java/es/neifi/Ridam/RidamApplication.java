package es.neifi.Ridam;

import es.neifi.Ridam.database.CollectionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoOperations;

@SpringBootApplication
public class RidamApplication {


	public static void main(String[] args) {

		SpringApplication.run(RidamApplication.class, args);
	}


}
