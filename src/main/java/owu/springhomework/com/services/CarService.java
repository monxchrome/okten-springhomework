package owu.springhomework.com.services;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import owu.springhomework.com.entities.Car;
import owu.springhomework.com.repositories.CarRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public List<Car> getAll() {
        return carRepository.findAll();
    }

    public Optional<Car> getById(ObjectId id) {
        return carRepository.findById(id);
    }

    public Car createCar(Car source) {
        return carRepository.save(source);
    }

    public void deleteCar(ObjectId id) {
        carRepository.deleteById(id);
    }
}
