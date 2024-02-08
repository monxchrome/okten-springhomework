package owu.springhomework.com.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import owu.springhomework.com.entities.Car;

@Repository
public interface CarRepository extends MongoRepository<Car, ObjectId> {
}
