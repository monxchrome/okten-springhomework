package owu.springhomework.com.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import owu.springhomework.com.dto.SearchCriteria;
import owu.springhomework.com.entities.Car;
import owu.springhomework.com.repository.CarRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CarController {

    private final CarRepository carRepository;

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> getCars() {
        List<Car> cars = carRepository.findAll();

        return ResponseEntity.ok(cars);
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> getById(@PathVariable("id") Long id) {
        Optional<Car> car = carRepository.findById(id);

        return ResponseEntity.of(car);
    }

    @GetMapping("/cars/power/{value}")
    public ResponseEntity<List<Car>> getByPower(@PathVariable("value") int power) {
        List<Car> cars = carRepository.findAllByPower(power);

        return ResponseEntity.ok(cars);
    }

    @GetMapping("/cars/producer/{value}")
    public ResponseEntity<List<Car>> getByProducer(@PathVariable("value") String producer) {
        List<Car> cars = carRepository.findAllByProducer(producer);

        return ResponseEntity.ok(cars);
    }

    @PostMapping("/cars")
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        Car createdCar = carRepository.save(car);
        URI uriOfCreatedCar = UriComponentsBuilder.fromPath("/cars/{id}").build(createdCar.getId());

        return ResponseEntity.created(uriOfCreatedCar).body(createdCar);
    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable("id") Long id) {
        carRepository.deleteById(id);

        return ResponseEntity.accepted().build();
    }

    @PostMapping("/cars/search")
    public ResponseEntity<List<Car>> searchCars(@RequestBody SearchCriteria searchCriteria) {
        Car probe = new Car();
        probe.setPower(searchCriteria.getPower());
        probe.setProducer(searchCriteria.getProducer());
        List<Car> result = carRepository.findAll(Example.of(probe));
        return ResponseEntity.ok(result);
    }
}
